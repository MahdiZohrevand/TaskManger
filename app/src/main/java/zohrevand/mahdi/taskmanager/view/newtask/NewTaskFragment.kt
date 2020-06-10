package zohrevand.mahdi.taskmanager.view.newtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import org.koin.android.viewmodel.ext.android.viewModel
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.FragmentNewTaskBinding
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter

class NewTaskFragment : Fragment() {

    private val newTaskViewModel: NewTaskViewModel by viewModel()
    private lateinit var binding: FragmentNewTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = newTaskViewModel

        binding.startNewTaskSpinnerMinute.setNumberAdapter(0,59,false)
        binding.startNewTaskSpinnerHour.setNumberAdapter(0,23,false)


        binding.endNewTaskSpinnerHour.setNumberAdapter(0,23,false)
        binding.endNewTaskSpinnerMinute.setNumberAdapter(0,59,false)

        return binding.root

    }
}