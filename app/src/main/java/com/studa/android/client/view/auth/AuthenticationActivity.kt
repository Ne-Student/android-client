package com.studa.android.client.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.studa.android.client.R
import com.studa.android.client.view.auth.landing.LandingPageFragment
import com.studa.android.client.view.main.MainActivity

private const val TAG = "AuthenticationActivity"

class AuthenticationActivity : AppCompatActivity(), FragmentChanger, ActivityChanger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LandingPageFragment.newInstance())
                .commit()
        }
    }

    override fun transitToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
//                R.anim.enter_from_right,
//                R.anim.exit_to_left,
//                R.anim.enter_from_left,
//                R.anim.exit_to_right
                R.anim.fragment_open_enter,
                R.anim.fragment_open_exit,
                R.anim.fragment_close_enter,
                R.anim.fragment_close_exit
            )
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun transitToActivity(intent: Intent) {
        startActivity(intent)
    }
}

interface FragmentChanger {
    fun transitToFragment(fragment: Fragment)
}

interface ActivityChanger {
    fun transitToActivity(intent: Intent)
}