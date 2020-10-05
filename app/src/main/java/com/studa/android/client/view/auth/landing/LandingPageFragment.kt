package com.studa.android.client.view.auth.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.studa.android.client.R
import com.studa.android.client.view.auth.ActivityChanger
import com.studa.android.client.view.auth.FragmentChanger
import com.studa.android.client.view.auth.landing.LandingPageFragment.Companion.newInstance
import com.studa.android.client.view.auth.login.LoginFragment
import com.studa.android.client.view.auth.register.RegisterFragment
import com.studa.android.client.view.main.MainActivity

class LandingPageFragment : Fragment() {
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var continueOfflineTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landing, container, false)

        loginButton = view.findViewById(R.id.login_button) as Button
        registerButton = view.findViewById(R.id.register_button) as Button
        continueOfflineTextView = view.findViewById(R.id.continue_offline) as TextView

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            transitToAnotherFragment(LoginFragment.newInstance())
        }

        registerButton.setOnClickListener {
            transitToAnotherFragment(RegisterFragment.newInstance())
        }

        continueOfflineTextView.setOnClickListener {
            val context = activity?.applicationContext
            context?.let {
                (activity as? ActivityChanger)?.
                    transitToActivity(MainActivity.newIntent(context))
            }
        }
    }

    private fun transitToAnotherFragment(fragment: Fragment) {
        (activity as? FragmentChanger)?.transitToFragment(fragment)
    }

    companion object {
        fun newInstance(): LandingPageFragment = LandingPageFragment()
    }
}