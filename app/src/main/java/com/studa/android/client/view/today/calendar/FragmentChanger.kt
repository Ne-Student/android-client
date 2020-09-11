package com.studa.android.client.view.today.calendar

import androidx.fragment.app.Fragment

interface FragmentChanger {
    fun replaceFragment(fragment: Fragment)
    fun addFragment(fragment: Fragment)
}