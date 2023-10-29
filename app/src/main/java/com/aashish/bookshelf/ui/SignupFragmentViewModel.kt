package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.model.User
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.CountryRepository
import com.aashish.bookshelf.repository.UserRepository
import com.aashish.bookshelf.utils.Resource
import com.aashish.bookshelf.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignupFragmentViewModel(
    private val authManager: AuthManager,
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _signupResultLiveData: MutableLiveData<Resource<User>> = MutableLiveData()
    val signupResultLiveData: LiveData<Resource<User>> = _signupResultLiveData

    private val _countryListLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val countryListLiveData: LiveData<List<String>> = _countryListLiveData

    private val _defaultCountryInDropdownLiveData: MutableLiveData<String> = MutableLiveData()
    val defaultCountryInDropdownLiveData: LiveData<String> = _defaultCountryInDropdownLiveData


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val defaultCountryDeferred = async { countryRepository.getCountryFromIpGeoLocation() }
            val countryList = countryRepository.getCountryList().toMutableList()
            val defaultCountry = defaultCountryDeferred.await() ?: "Other"
            if (!countryList.contains(defaultCountry)) {
                countryList.add(defaultCountry)
            }
            _countryListLiveData.postValue(countryList)
            _defaultCountryInDropdownLiveData.postValue(defaultCountry)
        }
    }

    fun processSignupCredentials(name: String, email: String, password: String, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val nameError = ValidationUtils.nameValidation(name).message
            val emailError = ValidationUtils.emailValidation(email).message
            val passwordError = ValidationUtils.passwordValidation(password).message

            val result = when {
                nameError != null -> Resource.Error(nameError)
                emailError != null -> Resource.Error(emailError)
                passwordError != null -> Resource.Error(passwordError)
                else -> {
                    val user = userRepository.getUserByEmail(email)
                    if (user != null) {
                        Resource.Error("Email already exists")
                    } else {
                        val newUser = User(name.trim(), email, password, country)
                        val generatedUserId = userRepository.registerNewUser(newUser)
                        authManager.updateLastLoginUserId(generatedUserId)
                        Resource.Success(newUser)
                    }
                }
            }
            _signupResultLiveData.postValue(result)
        }
    }
}

class SignupFragmentViewModelFactory(
    private val authManager: AuthManager,
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupFragmentViewModel::class.java)) {
            return SignupFragmentViewModel(authManager, userRepository, countryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}