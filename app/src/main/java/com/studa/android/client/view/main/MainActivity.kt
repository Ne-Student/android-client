package com.studa.android.client.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.studa.android.client.R
import com.studa.android.client.api.data_sources.LessonDataSource
import com.studa.android.client.utils.dpToInt
import com.studa.android.client.view.main.today.TodayFragment
import com.studa.android.client.view.main.today.calendar.FragmentChanger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentChanger {
    private lateinit var bottomNavigationView: BottomNavigationView

    @Inject
    lateinit var lessonService: LessonDataSource
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            replaceFragment(TodayFragment.newInstance())
        }

        bottomNavigationView = findViewById<BottomNavigationViewEx>(R.id.navigation_view)
            .apply {
                configureInitial()
            }
        
        lessonService.getAllLessonsForDate("24-12-2019")
            .subscribe({
                Log.d(TAG, "onCreate: $it")
            }, {
                Log.d(TAG, "onCreate: ${it.message}")
            })
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment
            )
            .commit()
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment
            )
            .addToBackStack(null)
            .commit()
    }

    private fun BottomNavigationViewEx.configureInitial() {
        setOnNavigationItemSelectedListener {
            setOnNavigationItemReselectedListener {}
            true
        }
        setIconSize(32F, 32F)
        itemHeight = dpToInt(65F, resources)
        setPadding(dpToInt(2F, resources))
        setLargeTextSize(16F)
        setSmallTextSize(12F)
        currentItem = 1
    }

    companion object {
        fun newIntent(packageContext: Context) =
            Intent(packageContext, MainActivity::class.java)
    }
}