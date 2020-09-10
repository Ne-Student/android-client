package com.nestudent.android.myapplication.view.today

import com.nestudent.android.myapplication.api.Repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nestudent.android.myapplication.R
import com.nestudent.android.myapplication.view.today.calendar.FragmentChanger
import com.nestudent.android.myapplication.view.today.bottomsheet.AddItemBottomDialogFragment
import com.nestudent.android.myapplication.view.today.calendar.CalendarFragment

private const val TAG = "TodayFragment"

class TodayFragment : Fragment() {
    private lateinit var dayTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var addButton: ImageButton
    private lateinit var testTextView: TextView
    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accessToken = Repository.instance.accessToken!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today, container, false)
        testTextView = (view.findViewById(R.id.test_tv) as TextView).apply {
            text = accessToken
        }

        val listener = View.OnClickListener {
            val calendarFragment = CalendarFragment()
            (activity as FragmentChanger).addFragment(calendarFragment)
        }

        dateTextView = (view.findViewById(R.id.text_date) as TextView).apply {
            setOnClickListener(listener)
        }

        dayTextView = (view.findViewById(R.id.text_day) as TextView).apply {
            setOnClickListener(listener)
        }

        addButton = (view.findViewById(R.id.add_button) as ImageButton).apply {
            setOnClickListener {
                val addPhotoBottomDialogFragment = AddItemBottomDialogFragment.newInstance()
                addPhotoBottomDialogFragment.show(childFragmentManager, "add_photo_dialog_fragment")
            }
        }
        return view
    }

    companion object {
        fun newInstance(): TodayFragment = TodayFragment()
    }
}