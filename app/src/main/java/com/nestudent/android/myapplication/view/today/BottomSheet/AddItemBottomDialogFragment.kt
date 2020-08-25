package com.nestudent.android.myapplication.view.today.BottomSheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nestudent.android.myapplication.R
import com.nestudent.android.myapplication.utils.transitions.BottomSheetSharedTransition
import com.nestudent.android.myapplication.utils.views.InvisibleFragment


class AddItemBottomDialogFragment: BottomSheetDialogFragment() {
    private lateinit var cancelButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_item_bottom_sheet, container, false)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.BottomSheetDialogTheme)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, InvisibleFragment())
            .commit()
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
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

    companion object {
        fun newInstance(): AddItemBottomDialogFragment {
            return AddItemBottomDialogFragment()
        }
    }
}