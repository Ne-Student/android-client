package com.nestudent.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nestudent.android.myapplication.api.Repository
import com.nestudent.android.myapplication.utils.SharedPreferencesWrapper
import com.nestudent.android.myapplication.view.login.LoginFragment
import com.nestudent.android.myapplication.view.today.TodayFragment

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val token = SharedPreferencesWrapper.getAccessToken(applicationContext)
        if (token != null) {
            Repository.instance.accessToken = token
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TodayFragment.newInstance())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                    LoginFragment()
                )
                .commit()
        }
    }
}