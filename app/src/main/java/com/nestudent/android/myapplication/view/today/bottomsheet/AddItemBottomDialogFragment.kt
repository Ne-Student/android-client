package com.nestudent.android.myapplication.view.today.bottomsheet

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.AutoTransition
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nestudent.android.myapplication.R
import com.nestudent.android.myapplication.utils.transitions.BottomSheetSharedTransition
import com.nestudent.android.myapplication.utils.views.InvisibleFragment
import com.nestudent.android.myapplication.utils.views.setTextSize
import com.nestudent.android.myapplication.utils.views.uncheckAllItems
import com.nestudent.android.myapplication.utils.views.updateIconSizes

private const val TAG = "BottomSheet"

class AddItemBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var cancelButton: ImageButton
    private lateinit var bottomNavigationView: BottomNavigationView

    private val viewModel by lazy {
        ViewModelProvider(this).get(BottomSheetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_item_bottom_sheet, container, false)

        cancelButton = (view.findViewById(R.id.cancel_button) as ImageButton).apply {
            setOnClickListener {
                dismiss()
            }
        }

        bottomNavigationView = (view.findViewById(R.id.navigation_view) as BottomNavigationView)
            .apply {
                uncheckAllItems()

                setOnNavigationItemSelectedListener {
                    if (viewModel.currentFragment == BottomSheetViewModel.CurrentFragment.UNSELECTED) {
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
                    updateIconSizes(ICON_SIZE_PRESSED, ICON_SIZE_DEFAULT)
                    true
                }
                setTextSize(20, 18)
            }

        if (viewModel.currentFragment == BottomSheetViewModel.CurrentFragment.UNSELECTED) {
            replaceWithEmptyFragment()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView.updateIconSizes(ICON_SIZE_PRESSED, ICON_SIZE_DEFAULT)
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

    private fun replaceWithEmptyFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, InvisibleFragment())
            .commit()
    }

    companion object {
        private const val ICON_SIZE_PRESSED = 34
        private const val ICON_SIZE_DEFAULT = 32

        fun newInstance(): AddItemBottomDialogFragment {
            return AddItemBottomDialogFragment()
        }
    }
}