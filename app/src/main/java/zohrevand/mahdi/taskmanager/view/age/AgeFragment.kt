package zohrevand.mahdi.taskmanager.view.age

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.age_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.AgeFragmentBinding
import zohrevand.mahdi.calendar.persian.PersianCalendar
import zohrevand.mahdi.taskmanager.utils.onItemSelected
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter
import zohrevand.mahdi.taskmanager.utils.setPosition
import java.util.*

class AgeFragment : Fragment() {


    private lateinit var binding: AgeFragmentBinding


    companion object {
        fun newInstance() = AgeFragment()
    }

    private val viewModel: AgeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.age_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apply {
            yearSpinner.setNumberAdapter(1300, PersianCalendar(Calendar.getInstance()).year, true)
            monthSpinner.setNumberAdapter(1, 12, false)
            daySpinner.setNumberAdapter(1, 31, false)
        }


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}
