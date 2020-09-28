package com.studa.android.client.view.today.bottomsheet

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.AutoTransition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.studa.android.client.view.today.bottomsheet.BottomSheetViewModel.CurrentFragment
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.studa.android.client.R
import com.studa.android.client.utils.dpToInt
import com.studa.android.client.utils.transitions.BottomSheetSharedTransition
import com.studa.android.client.utils.views.InvisibleFragment

private const val TAG = "BottomSheet"

class AddItemBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var cancelButton: ImageButton
    private lateinit var bottomNavigationView: BottomNavigationViewEx

    private val viewModel by lazy {
        ViewModelProvider(this).get(BottomSheetViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dia ->
            val dialog = dia as? BottomSheetDialog
            val bottomSheet = dialog?.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            bottomSheet?.let {
                BottomSheetBehavior.from(bottomSheet).state =
                    BottomSheetBehavior.STATE_EXPANDED
                BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
                BottomSheetBehavior.from(bottomSheet).isHideable = true
            }
        }
        return bottomSheetDialog
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

        cancelButton = view.findViewById(R.id.cancel_button) as ImageButton
        bottomNavigationView = view.findViewById(R.id.navigation_view)
                as BottomNavigationViewEx

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton.apply {
            setOnClickListener {
                dismiss()
            }
        }

        bottomNavigationView.apply {
            configureInitial()
        }

        if (viewModel.currentFragment == CurrentFragment.UNSELECTED) {
            replaceWithEmptyFragment()
        }
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

    private fun BottomNavigationViewEx.configureInitial() {
        uncheckAllItems()
        setIconSize(48F, 48F)
        itemHeight = dpToInt(88F, resources)
        setPadding(dpToInt(10F, resources))
        setLargeTextSize(20F)
        setSmallTextSize(18F)
        setOnNavigationItemSelectedListener {
            if (viewModel.currentFragment == CurrentFragment.UNSELECTED) {
                setOnNavigationItemReselectedListener {}
            }

            val newFragment = when (it.itemId) {
                R.id.lesson -> {
                    viewModel.currentFragment = CurrentFragment.LESSON_FRAGMENT
                    AddLessonFragment.newInstance()
                }
                R.id.teacher -> {
                    viewModel.currentFragment = CurrentFragment.TEACHER_FRAGMENT
                    AddTeacherFragment.newInstance()
                }
                else -> {
                    viewModel.currentFragment = CurrentFragment.TASK_FRAGMENT
                    AddTaskFragment.newInstance()
                }
            }
            newFragment.sharedElementEnterTransition = AutoTransition()

            transitToFragment(newFragment)
            true
        }
    }

    private fun replaceWithEmptyFragment() =
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, InvisibleFragment())
            .commit()

    companion object {
        fun newInstance(): AddItemBottomDialogFragment {
            return AddItemBottomDialogFragment()
        }
    }
}