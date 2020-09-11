package com.studa.android.client.utils

import android.content.res.Resources
import android.util.TypedValue

fun dpToInt(dp: Float, resources: Resources): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    ).toInt()