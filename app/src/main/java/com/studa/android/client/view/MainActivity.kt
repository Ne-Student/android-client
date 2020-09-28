package com.studa.android.client.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.studa.android.client.R
import com.studa.android.client.api.Repository
import com.studa.android.client.api.model.Teacher
import com.studa.android.client.api.services.teacher.TeacherServiceImpl
import com.studa.android.client.utils.dpToInt
import com.studa.android.client.view.today.TodayFragment
import com.studa.android.client.view.today.calendar.FragmentChanger
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), FragmentChanger {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(TodayFragment.newInstance())

        bottomNavigationView = (findViewById(R.id.navigation_view) as BottomNavigationViewEx)
            .apply {
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

}