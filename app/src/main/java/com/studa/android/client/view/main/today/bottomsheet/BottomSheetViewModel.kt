package com.studa.android.client.view.main.today.bottomsheet

import androidx.lifecycle.ViewModel

class BottomSheetViewModel : ViewModel() {
    var currentFragment: CurrentFragment = CurrentFragment.UNSELECTED

    enum class CurrentFragment {
        TEACHER_FRAGMENT, TASK_FRAGMENT, LESSON_FRAGMENT, UNSELECTED
    }
}