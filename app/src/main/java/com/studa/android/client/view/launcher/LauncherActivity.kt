package com.studa.android.client.view.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studa.android.client.R
import com.studa.android.client.api.network_wrapper.NetworkWrapper
import com.studa.android.client.utils.shared_preferences.SharedPreferencesWrapper
import com.studa.android.client.view.auth.AuthenticationActivity
import com.studa.android.client.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
    @Inject
    lateinit var networkWrapper: NetworkWrapper

    @Inject
    lateinit var sharedPreferencesWrapper: SharedPreferencesWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreenTheme)
        super.onCreate(savedInstanceState)

        // TODO: Delete this on backend implementation finish
        // sharedPreferencesWrapper.saveAccessToken("token")

        Single.fromCallable {
            sharedPreferencesWrapper.getAccessToken()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                if (token != null) {
                    networkWrapper.saveAccessToken(token)
                    startActivity(MainActivity::class.java)
                    overridePendingTransition(0, 0)
                } else {
                    startActivity(AuthenticationActivity::class.java)
                    overridePendingTransition(0, 0)
                }
            }, {
                startActivity(AuthenticationActivity::class.java)
            })
    }

    private fun <T> startActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
    }
}