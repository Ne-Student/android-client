package com.nestudent.android.myapplication.view.today.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nestudent.android.myapplication.R

class AddTeacherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_teacher, container, false)
    }

    companion object {
        fun newInstance(): AddTeacherFragment {
            return AddTeacherFragment()
        }
    }
}