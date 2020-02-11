package com.haomins.reader.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.activities.main.MainActivity
import com.haomins.reader.models.user.User
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private val loginViewModel by lazy {
        (activity as MainActivity).loginViewModel
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
        login_button.setOnClickListener {
            loginButtonOnClick()
        }
    }

    private fun loginButtonOnClick() {
        loginViewModel.login(
            User(
                userEmail = login_username_edit_text.text.toString(),
                userPassword = login_password_edit_text.text.toString()
            ), application = (activity?.application as ReaderApplication)
        ) {
            //TODO: Handler login success ui change here, this is a callback to the observer
        }
    }
}