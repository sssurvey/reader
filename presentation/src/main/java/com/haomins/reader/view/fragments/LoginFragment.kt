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
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel by viewModels<LoginViewModel> { viewModelFactory }

    private val isUserLoggedInObserver by lazy {
        Observer<Boolean>() {
            if (it) {
                popItself()
                showSourceTitleListFragment()
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as ReaderApplication).appComponent.viewModelComponent().build().inject(this)
        super.onAttach(context)
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
                    if (it.isEmpty()) setTextColor(requireContext().getColor(R.color.colorPrimary))
                    else setTextColor(requireContext().getColor(R.color.lightGray))
                }
            }
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