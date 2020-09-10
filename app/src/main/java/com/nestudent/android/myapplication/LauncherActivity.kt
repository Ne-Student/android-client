package com.nestudent.android.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nestudent.android.myapplication.api.Repository
import com.nestudent.android.myapplication.view.today.calendar.FragmentChanger
import com.nestudent.android.myapplication.utils.SharedPreferencesWrapper
import com.nestudent.android.myapplication.view.login.LoginFragment
import com.nestudent.android.myapplication.view.today.TodayFragment

private const val TAG = "MainActivity"

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val token = SharedPreferencesWrapper.getAccessToken(applicationContext)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (token != null && fragment == null) {
            Repository.instance.accessToken = token
            startActivity(MainActivity::class.java)
        } else if (token == null) {
            startActivity(AuthenticationActivity::class.java)
        }
    }

    private fun <T> startActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}