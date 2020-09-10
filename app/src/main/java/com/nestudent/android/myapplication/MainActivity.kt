package com.nestudent.android.myapplication

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nestudent.android.myapplication.utils.views.setTextSize
import com.nestudent.android.myapplication.utils.views.updateIconSizes
import com.nestudent.android.myapplication.view.today.TodayFragment
import com.nestudent.android.myapplication.view.today.bottomsheet.*
import com.nestudent.android.myapplication.view.today.calendar.FragmentChanger

class MainActivity : AppCompatActivity(), FragmentChanger {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(TodayFragment.newInstance())

        bottomNavigationView = (findViewById(R.id.navigation_view) as BottomNavigationView)
            .apply {
                setOnNavigationItemSelectedListener {
                    setOnNavigationItemReselectedListener {}
                    true
                }
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