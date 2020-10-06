package zohrevand.mahdi.taskmanager.view.newtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.android.viewmodel.ext.android.viewModel
import zohrevand.mahdi.taskmanager.NavigationCommand
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.FragmentNewTaskBinding
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter

class NewTaskFragment : Fragment() {

    private val args: NewTaskFragmentArgs by navArgs()
    private val newTaskViewModel: NewTaskViewModel by viewModel()
    private lateinit var binding: FragmentNewTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            if (it.containsKey("task")) {
                val task = NewTaskFragmentArgs.fromBundle(it).task
                if (task != null) {
                    newTaskViewModel.setTask(task)
                }
            }
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = newTaskViewModel

        binding.startNewTaskSpinnerHour.setNumberAdapter(0, 23, false)
        binding.startNewTaskSpinnerMinute.setNumberAdapter(0, 59, false)
        binding.endNewTaskSpinnerHour.setNumberAdapter(0, 23, false)
        binding.endNewTaskSpinnerMinute.setNumberAdapter(0, 59, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newTaskViewModel.navigation.observeForever {
            when (it) {
                is NavigationCommand.Back -> {
                    findNavController().navigateUp()
                }
            }
        }

        newTaskViewModel.navigation.observe(this, Observer {
            when (it) {
                is NavigationCommand.Back -> findNavController().navigateUp()
            }
        })
    }
}