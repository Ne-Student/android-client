package com.studa.android.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studa.android.client.api.Repository
import com.studa.android.client.api.model.AccessToken
import com.studa.android.client.utils.getAccessToken
import com.studa.android.client.utils.saveAccessToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "MainActivity"

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        saveAccessToken(applicationContext, AccessToken("token"))
        Single.fromCallable {
            getAccessToken(applicationContext)
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