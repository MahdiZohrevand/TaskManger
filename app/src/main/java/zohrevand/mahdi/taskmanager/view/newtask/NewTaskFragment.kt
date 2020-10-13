package zohrevand.mahdi.taskmanager.view.newtask

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.paris.extensions.paddingDp
import com.airbnb.paris.extensions.style
import org.koin.android.viewmodel.ext.android.viewModel
import zohrevand.mahdi.taskmanager.NavigationCommand
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.FragmentNewTaskBinding
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter
import zohrevand.mahdi.taskmanager.view.MainActivity

class NewTaskFragment : Fragment() {


    private var icon: ImageView? = null
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
                    (activity as MainActivity).setToolbarTitle("ویرایش تسک")
                    addIconToToolbar()
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
        binding.dateDay.setNumberAdapter(1, 30, false)
        binding.dateMonth.setNumberAdapter(1, 12, false)
        binding.dateYear.setNumberAdapter(0, 1500, false)

        newTaskViewModel.maxMonth = { max, dayPosition ->
            binding.dateDay.setNumberAdapter(1, max, false)
            binding.dateDay.setSelection(if (dayPosition >= max) max - 1 else dayPosition)
        }

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

    @Suppress("DEPRECATION")
    @SuppressLint("RtlHardcoded")
    private fun addIconToToolbar() {

        val toolbar = (activity as MainActivity).getToolbar()


        val params: Toolbar.LayoutParams =
            Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )
        params.gravity = Gravity.LEFT


        icon =
            ImageView(context).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setImageDrawable(
                        context?.resources?.getDrawable(
                            R.drawable.ic_delete_forever_24,
                            null
                        )
                    )
                } else {
                    setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_delete_forever_24))
                }

            }
        icon?.style {
            paddingDp(8)
        }

        icon?.layoutParams = params


        icon?.setOnClickListener {
            newTaskViewModel.deleteTask()
            toolbar.removeView(icon)
            findNavController().navigateUp()
        }

        toolbar.addView(icon)
    }


    override fun onDestroy() {
        super.onDestroy()
        icon?.let {
            (activity as MainActivity).getToolbar().removeView(it)
        }

    }
}