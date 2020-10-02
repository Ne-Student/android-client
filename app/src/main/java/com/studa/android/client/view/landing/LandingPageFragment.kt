package com.studa.android.client.view.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.studa.android.client.R
import com.studa.android.client.view.FragmentChanger
import com.studa.android.client.view.login.LoginFragment
import com.studa.android.client.view.today.TodayFragment

class LandingPageFragment: Fragment() {
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landing, container, false)

        loginButton = view.findViewById(R.id.login_button) as Button
        registerButton = view.findViewById(R.id.register_button) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            transitToAnotherFragment(LoginFragment())
        }

        registerButton.setOnClickListener {
            transitToAnotherFragment(TodayFragment())
        }
    }

    private fun transitToAnotherFragment(fragment: Fragment) {
        (activity as FragmentChanger).transitToFragment(fragment)
    }
}