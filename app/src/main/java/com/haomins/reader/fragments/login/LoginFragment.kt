package com.haomins.reader.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.activities.main.MainActivity
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
        login_button.setOnClickListener {
            loginButtonOnClick()
        }
    }

    private fun registerLiveDataObserver() {
        loginViewModel.isUserLoggedIn.observe(this, isUserLoggedInObserver)
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