package com.studa.android.client.view.main.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studa.android.client.R
import com.studa.android.client.api.network_wrapper.NetworkWrapper
import com.studa.android.client.view.main.today.calendar.FragmentChanger
import com.studa.android.client.view.main.today.bottomsheet.AddItemBottomDialogFragment
import com.studa.android.client.view.main.today.calendar.CalendarFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodayFragment : Fragment() {
    private lateinit var dayTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var addButton: ImageButton
    private lateinit var accessToken: String
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var networkWrapper: NetworkWrapper

    private val list = arrayListOf(
        "hello", "test", "asdg", "asdg", "sdgsdg",
        "hello", "test", "asdg", "asdg", "sdgsdg",
        "hello", "test", "asdg", "asdg", "sdgsdg",
        "hello", "test", "asdg", "asdg", "sdgsdg",
        "hello", "test", "asdg", "asdg", "sdgsdg",
        "hello", "test", "asdg", "asdg", "sdgsdg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accessToken = try {
            networkWrapper.getAccessToken()!!
        } catch (e: Exception) {
            // TODO: revisit this, potentially move user to login activity
            Log.d(TAG, "Error unwrapping access token. ${e.message}")
            ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today, container, false)

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

        recyclerView = (view.findViewById(R.id.recycler_view) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TodayAdaper()
        }
        return view
    }

    inner class TodayAdaper : RecyclerView.Adapter<TodayAdaper.TodayItemHolder>() {
        inner class TodayItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            fun bind(text: String) {
                textView.text = text
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayItemHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.today_item, parent, false)

            return TodayItemHolder(view)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: TodayItemHolder, position: Int) =
            holder.bind(list[position])
    }


    companion object {
        private const val TAG = "TodayFragment"
        fun newInstance(): TodayFragment = TodayFragment()
    }
}