package com.nestudent.android.myapplication.view.today.calendar

import androidx.fragment.app.Fragment

interface FragmentChanger {
    fun replaceFragment(fragment: Fragment)
    fun addFragment(fragment: Fragment)
}