package com.nestudent.android.myapplication.view.today.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.AutoTransition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.nestudent.android.myapplication.R
import com.nestudent.android.myapplication.utils.transitions.BottomSheetSharedTransition
import com.nestudent.android.myapplication.utils.views.InvisibleFragment


private const val TAG = "BottomSheet"

class AddItemBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var cancelButton: ImageButton
    private lateinit var bottomNavigationView: BottomNavigationViewEx

    private val viewModel by lazy {
        ViewModelProvider(this).get(BottomSheetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.dialog_add_item_bottom_sheet,
            container,
            false
        )

        cancelButton = (view.findViewById(R.id.cancel_button) as ImageButton).apply {
            setOnClickListener {
                dismiss()
            }
        }

        bottomNavigationView = (view.findViewById(R.id.navigation_view)
                as BottomNavigationViewEx).apply {
            uncheckAllItems()
            setIconSize(36F, 36F)
            itemHeight = dpToInt(80F)
            setPadding(dpToInt(10F))
            setLargeTextSize(20F)
            setSmallTextSize(18F)
            setOnNavigationItemSelectedListener {
                if (viewModel.currentFragment ==
                    BottomSheetViewModel.CurrentFragment.UNSELECTED
                ) {
                    setOnNavigationItemReselectedListener {}
                }

                val newFragment = when (it.itemId) {
                    R.id.lesson -> {
                        viewModel.currentFragment =
                            BottomSheetViewModel.CurrentFragment.LESSON_FRAGMENT
                        AddLessonFragment.newInstance()
                    }
                    R.id.teacher -> {
                        viewModel.currentFragment =
                            BottomSheetViewModel.CurrentFragment.TEACHER_FRAGMENT
                        AddTeacherFragment.newInstance()
                    }
                    else -> {
                        BottomSheetViewModel.CurrentFragment.TASK_FRAGMENT
                        AddTaskFragment.newInstance()
                    }
                }.apply {
                    sharedElementEnterTransition = AutoTransition()
                }

                transitToFragment(newFragment)
                true
            }
        }

        if (viewModel.currentFragment ==
            BottomSheetViewModel.CurrentFragment.UNSELECTED
        ) {
            replaceWithEmptyFragment()
        }


        return view
    }

    private fun transitToFragment(newFragment: Fragment) {
        val currentFragmentRoot = childFragmentManager.findFragmentById(R.id.fragment_container)
            ?.requireView()

        childFragmentManager
            .beginTransaction()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (currentFragmentRoot != null) {
                        addSharedElement(currentFragmentRoot, currentFragmentRoot.transitionName)
                    }
                    setReorderingAllowed(true)

                    newFragment.sharedElementEnterTransition = BottomSheetSharedTransition()
                }
            }
            .replace(R.id.fragment_container, newFragment)
            .commit()
    }

    private fun BottomNavigationViewEx.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

    private fun replaceWithEmptyFragment() =
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, InvisibleFragment())
            .commit()

    private fun dpToInt(dp: Float): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
        ).toInt()

    companion object {
        fun newInstance(): AddItemBottomDialogFragment {
            return AddItemBottomDialogFragment()
        }
    }
}