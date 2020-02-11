package com.haomins.reader.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            loginViewModel.login("NOT", "FINISHED")
        }
    }
}