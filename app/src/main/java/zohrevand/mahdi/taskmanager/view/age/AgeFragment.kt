package zohrevand.mahdi.taskmanager.view.age

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter

class AgeFragment : Fragment() {


    companion object {
        fun newInstance() = AgeFragment()
    }

    private lateinit var viewModel: AgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.age_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val yearSpinner: Spinner = view.findViewById(R.id.year_spinner)
        val monthSpinner: Spinner = view.findViewById(R.id.month_spinner)
        val daySpinner: Spinner = view.findViewById(R.id.day_spinner)
        yearSpinner.setNumberAdapter(1300, 1398)
        monthSpinner.setNumberAdapter(1, 12)
        daySpinner.setNumberAdapter(1, 31)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AgeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setNumberAdapterForSpinner(spinner: Spinner, min: Int, max: Int) {
        val numberList = mutableListOf<Int>()
        for (element in max downTo min) {
            numberList.add(element)
        }
        val adapter =
            ArrayAdapter<Int>(context!!, android.R.layout.simple_spinner_dropdown_item, numberList)
        spinner.adapter = adapter
    }


}
