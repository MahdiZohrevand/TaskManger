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

import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.AgeFragmentBinding
import zohrevand.mahdi.taskmanager.utils.onItemSelected
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter

class AgeFragment : Fragment() {


    private lateinit var binding: AgeFragmentBinding


    companion object {
        fun newInstance() = AgeFragment()
    }

    private lateinit var viewModel: AgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.age_fragment, container, false)
        binding.apply {
            yearSpinner.setNumberAdapter(1300, 1398)
            monthSpinner.setNumberAdapter(1, 12)
            daySpinner.setNumberAdapter(1, 31)


            yearSpinner.onItemSelected {
                viewModel?.year = it
            }

            monthSpinner.onItemSelected {
                viewModel?.month = it
            }

            daySpinner.onItemSelected {
                viewModel?.day = it
            }


        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AgeViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.userAge.observe(this , Observer {
            binding.userAgeTxt.text = it
        })
    }


}
