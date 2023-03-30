package com.haomins.reader.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.haomins.reader.BuildConfig
import com.haomins.reader.R
import com.haomins.reader.databinding.FragmentLoginBinding
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    private val isUserLoggedInObserver by lazy {
        Observer<Boolean> {
            if (it) {
                popItself()
                showSourceTitleListFragment()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLiveDataObserver()
        initiateUI()
        setOnClickListener()
        setOnTextChangedListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            loginButton.setOnClickListener { loginButtonOnClick() }
            signUpButton.setOnClickListener { signUpButtonOnClick() }
            forgotPassword.setOnClickListener { forgetPasswordButtonOnClick() }
            newsAppDisclosure.setOnClickListener { showDisclosureFragment() }
        }
    }

    private fun showDisclosureFragment() {
        (requireActivity() as? MainActivity)?.showDisclosureFragment()
    }

    private fun setOnTextChangedListener() {
        setLoginPasswordEditTextOnChangeListener()
    }

    private fun setLoginPasswordEditTextOnChangeListener() {
        with(binding) {
            loginPasswordEditText.addTextChangedListener {
                loginButton.isEnabled = !it.isNullOrEmpty()
            }
        }
    }

    private fun initiateUI() {
        binding.loginAppVersionTextView.text =
            getString(R.string.version_description, BuildConfig.VERSION_NAME)
    }

    private fun registerLiveDataObserver() {
        loginViewModel.isUserLoggedIn.observe(viewLifecycleOwner, isUserLoggedInObserver)
    }

    private fun signUpButtonOnClick() {
        loginViewModel.onSignUp {
            startActivity(Intent(Intent.ACTION_VIEW, it))
        }
    }

    private fun forgetPasswordButtonOnClick() {
        loginViewModel.onForgetPassword {
            startActivity(Intent(Intent.ACTION_VIEW, it))
        }
    }

    private fun loginButtonOnClick() {
        with(binding) {
            loginViewModel.authorize(
                userName = loginUsernameEditText.text.toString(),
                userPassword = loginPasswordEditText.text.toString()
            ) {
                showToast(it)
            }
        }
    }

    private fun popItself() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun showSourceTitleListFragment() {
        activity?.let {
            (it as MainActivity).showAfterUserLoggedInFragment()
        }
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}