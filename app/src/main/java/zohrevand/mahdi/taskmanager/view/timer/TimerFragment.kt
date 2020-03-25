package zohrevand.mahdi.taskmanager.view.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import zohrevand.mahdi.taskmanager.R

class TimerFragment : Fragment() {

    private lateinit var timerViewModel: TimerViewModel
    private lateinit var button: ToggleButton
    private val args: TimerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        timerViewModel =
            ViewModelProviders.of(this).get(TimerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        button = root.findViewById(R.id.timer_start_btn)
        button.setOnCheckedChangeListener { buttonView, isChecked ->

        }



        timerViewModel.text.observe(this, Observer {
            //  textView.text = it
        })
        val item = args.task
        item.let {
            textView.text = it.toString()
        }


        return root
    }
}