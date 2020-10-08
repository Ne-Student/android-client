package com.studa.android.client.view.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.studa.android.client.R
import com.studa.android.client.view.auth.FragmentChanger
import com.studa.android.client.view.auth.register.RegisterFragment

class LoginFragment : Fragment() {
    private lateinit var backButton: ImageButton
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        backButton = view.findViewById(R.id.back_button)
        registerButton = view.findViewById(R.id.register_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        registerButton.setOnClickListener {
            (activity as? FragmentChanger)?.replaceFragment(RegisterFragment.newInstance())
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}