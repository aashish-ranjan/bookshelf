package com.aashish.bookshelf.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aashish.bookshelf.R
import com.aashish.bookshelf.databinding.FragmentLoginBinding
import com.aashish.bookshelf.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding is null. Is the view visible?"
        }

    private val viewModel: LoginFragmentViewModel by viewModels()

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

        viewModel.userEmailLiveData.observe(viewLifecycleOwner) { userEmail ->
            if (userEmail.isNullOrEmpty()) {
                setupNewLogin()
            } else {
                setupReturnLogin(userEmail)
            }
        }
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) { loginResult ->
            when (loginResult) {
                is Resource.Success -> {
                    binding.loginProgressBar.isVisible = false
                    val intent = Intent(requireActivity(), BooksActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                is Resource.Error -> {
                    binding.loginProgressBar.isVisible = false
                    Snackbar.make(
                        binding.root,
                        loginResult.message ?: getString(R.string.unknown_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    binding.loginProgressBar.isVisible = false
                }
            }

        }

        setupClickListeners()
    }

    private fun setupReturnLogin(userEmail: String) {
        binding.apply {
            etEmail.isEnabled = false
            etEmail.setText(userEmail)
            tvLogout.isVisible = true
            tvSignup.isVisible = false
        }
    }

    private fun setupNewLogin() {
        binding.apply {
            etEmail.isEnabled = true
            etEmail.setText("")
            tvLogout.isVisible = false
            tvSignup.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.processLoginCredentials(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
            tvSignup.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
                findNavController().navigate(action)
            }
            tvLogout.setOnClickListener {
                Snackbar.make(
                    binding.root,
                    getString(R.string.logged_out_successfully),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.logout()
            }
        }
    }
}