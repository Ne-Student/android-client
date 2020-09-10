package com.nestudent.android.myapplication.utils.views

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setTextSize(
    chosenItemTextSize: Int,
    notChosenItemTextSize: Int
) {
    for (menuItem in iterator()) {
        val _small =
            menuItem.findViewById(com.google.android.material.R.id.smallLabel) as View
        if (_small is TextView) {
            _small.textSize = notChosenItemTextSize.toFloat()
        }
        val _large =
            menuItem.findViewById(com.google.android.material.R.id.largeLabel) as View
        if (_large is TextView) {
            _large.textSize = chosenItemTextSize.toFloat()
        }
    }
}

fun BottomNavigationView.iterator() = iterator<BottomNavigationItemView> {
    for (i in 0 until childCount) {
        val item = getChildAt(i)
        if (item is BottomNavigationMenuView) {
            for (j in 0 until item.childCount) {
                val menuItem = item.getChildAt(j)
                if (menuItem is BottomNavigationItemView) {
                    yield(menuItem)
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.updateIconSizes(
    chosenItemIconSize: Int,
    notChosenItemIconSize: Int
) {
    for (menuItem in iterator()) {
        menuItem.setIconSize(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                if (menuItem.isPressed)
                    chosenItemIconSize.toFloat()
                else
                    notChosenItemIconSize.toFloat(),
                resources.displayMetrics
            ).toInt()
        )
    }
}

fun BottomNavigationView.uncheckAllItems() {
    menu.setGroupCheckable(0, true, false)
    for (i in 0 until menu.size()) {
        menu.getItem(i).isChecked = false
    }
    menu.setGroupCheckable(0, true, true)
}