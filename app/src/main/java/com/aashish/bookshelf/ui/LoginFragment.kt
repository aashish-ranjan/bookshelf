package com.aashish.bookshelf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aashish.bookshelf.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null. Is the view visible?"
        }

    private lateinit var viewModel: LoginFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authManager = (requireActivity() as AuthActivity).authManager
        val userRepository = (requireActivity() as AuthActivity).userRepository
        val loginFragmentViewModelFactory =
            LoginFragmentViewModelFactory(authManager, userRepository)
        viewModel = ViewModelProvider(this, loginFragmentViewModelFactory)[LoginFragmentViewModel::class.java]

        viewModel.userEmailLiveData.observe(viewLifecycleOwner) { userEmail ->
            userEmail?.let {
                binding.etEmail.setText(userEmail)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}