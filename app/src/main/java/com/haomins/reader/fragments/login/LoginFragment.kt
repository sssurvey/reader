package com.haomins.reader.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.haomins.reader.R
import com.haomins.reader.activities.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private val loginViewModel by lazy {
        (activity as MainActivity).loginViewModel
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
        activity?.let {
            it.supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun showSourceTitleListFragment() {
        activity?.let {
            (it as MainActivity).showSourceTitleListFragment()
        }
    }
}