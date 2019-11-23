package zohrevand.mahdi.taskmanager.view.newtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.FragmentNewTaskBinding

class NewTask : Fragment() {

    private lateinit var newTaskViewModel: NewTaskViewModel
    private lateinit var binding: FragmentNewTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false)

        newTaskViewModel = ViewModelProviders.of(this).get(NewTaskViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = newTaskViewModel

        return binding.root
    }
}