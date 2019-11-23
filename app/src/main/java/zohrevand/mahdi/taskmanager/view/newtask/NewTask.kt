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

class NewTask : Fragment() {

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

        return binding.root
    }
}