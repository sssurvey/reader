package com.haomins.reader.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.BuildConfig
import com.haomins.reader.R
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.LoginViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }

    private val isUserLoggedInObserver by lazy {
        Observer<Boolean>() {
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
        login_sign_up_text_view.setOnClickListener { loginSignUpDescriptionOnClick() }
    }

    private fun setOnTextChangedListener() {
        setLoginPasswordEditTextOnChangeListener()
    }

    private fun setLoginPasswordEditTextOnChangeListener() {
        login_password_edit_text.addTextChangedListener {
            it?.let {
                sign_up_button.apply {
                    isEnabled = it.isEmpty()
                    if (it.isEmpty()) setTextColor(context!!.getColor(R.color.colorPrimary))
                    else setTextColor(context!!.getColor(R.color.lightGray))
                }
            }
        }
    }

    private fun initiateUI() {
        login_app_version_text_view.text =
            getString(R.string.version_description, BuildConfig.VERSION_NAME)
    }

    private fun registerLiveDataObserver() {
        loginViewModel.isUserLoggedIn.observe(this, isUserLoggedInObserver)
    }

    private fun signUpButtonOnClick() {
        startActivity(Intent(Intent.ACTION_VIEW, loginViewModel.getSignUpUrl()))
    }

    private fun loginSignUpDescriptionOnClick() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                loginViewModel.getGenerateAccountForGoogleOrFacebookUrl()
            )
        )
    }

    private fun loginButtonOnClick() {
        loginViewModel.authorize(
            user = Pair(
                login_username_edit_text.text.toString(),
                login_password_edit_text.text.toString()
            )
        )
    }

    private fun popItself() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun showSourceTitleListFragment() {
        activity?.let {
            (it as MainActivity).showSourceTitleListFragment()
        }
    }
}