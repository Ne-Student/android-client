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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "MainActivity"

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        Single.fromCallable {
            SharedPreferencesWrapper.getAccessToken(applicationContext)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                if (token != null && fragment == null) {
                    Repository.instance.accessToken = token
                    startActivity(MainActivity::class.java)
                    overridePendingTransition(0, 0)
                } else if (token == null) {
                    startActivity(AuthenticationActivity::class.java)
                    overridePendingTransition(0, 0)
                }
            }, {
                startActivity(AuthenticationActivity::class.java)
            })
    }

    private fun <T> startActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}