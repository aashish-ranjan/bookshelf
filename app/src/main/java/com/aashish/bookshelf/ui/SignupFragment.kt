package com.aashish.bookshelf.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aashish.bookshelf.databinding.FragmentSignupBinding
import com.aashish.bookshelf.utils.Resource
import com.google.android.material.snackbar.Snackbar

class SignupFragment: Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null. Is the view visible?"
        }

    private lateinit var viewModel: SignupFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authManager = (requireActivity() as AuthActivity).authManager
        val userRepository = (requireActivity() as AuthActivity).userRepository
        val signupFragmentViewModelFactory =
            SignupFragmentViewModelFactory(authManager, userRepository)
        viewModel = ViewModelProvider(this, signupFragmentViewModelFactory)[SignupFragmentViewModel::class.java]

        setUpClickListeners()
        observeListeners()
    }

    private fun setUpClickListeners() {
        with(binding) {
            btnSignup.setOnClickListener {
                viewModel.processSignupCredentials(
                    etName.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    spinnerCountry.selectedItem.toString()
                )
            }
        }
    }

    private fun observeListeners() {
        viewModel.signupResultLiveData.observe(viewLifecycleOwner) { signupResult ->
            when (signupResult) {
                is Resource.Success -> {
                    binding.signupProgressBar.isVisible = false
                    val intent = Intent(requireActivity(), BookListActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                is Resource.Error -> {
                    binding.signupProgressBar.isVisible = false
                    Snackbar.make(binding.root, signupResult.message ?: "Unknown Error", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.signupProgressBar.isVisible = false
                }
            }
        }
        viewModel.countryListLiveData.observe(viewLifecycleOwner) { countryList ->
            if (countryList.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, countryList)
                binding.spinnerCountry.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}