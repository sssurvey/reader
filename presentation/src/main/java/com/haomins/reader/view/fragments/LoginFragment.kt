package com.haomins.reader.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.BuildConfig
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()

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
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLiveDataObserver()
        initiateUI()
        setOnClickListener()
        setOnTextChangedListener()
    }

    private fun setOnClickListener() {
        login_button.setOnClickListener { loginButtonOnClick() }
        sign_up_button.setOnClickListener { signUpButtonOnClick() }
        forgot_password.setOnClickListener { forgetPasswordButtonOnClick() }
        news_app_disclosure.setOnClickListener { showDisclosureFragment() }
    }

    private fun showDisclosureFragment() {
        (requireActivity() as? MainActivity)?.showDisclosureFragment()
    }

    private fun setOnTextChangedListener() {
        setLoginPasswordEditTextOnChangeListener()
    }

    private fun setLoginPasswordEditTextOnChangeListener() {
        login_password_edit_text.addTextChangedListener {
            login_button.isEnabled = !it.isNullOrEmpty()
        }
    }

    private fun initiateUI() {
        login_app_version_text_view.text =
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
        loginViewModel.authorize(
            userName = login_username_edit_text.text.toString(),
            userPassword = login_password_edit_text.text.toString()
        ) {
            showToast(it)
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