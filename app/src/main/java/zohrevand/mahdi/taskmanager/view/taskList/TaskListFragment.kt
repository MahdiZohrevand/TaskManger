package zohrevand.mahdi.taskmanager.view.taskList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import zohrevand.mahdi.customviewtest.model.CalendarTask
import zohrevand.mahdi.customviewtest.model.Task
import zohrevand.mahdi.taskmanager.NavigationCommand
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.utils.SingleLiveEvent
import zohrevand.mahdi.taskmanager.view.newtask.NewTaskFragmentArgs

import zohrevand.mahdi.taskmanager.view.taskList.dummy.DummyContent


class TaskListFragment : Fragment() {


    val db: TaskManagerDatabase by inject()


    private val listVewModel: TaskListVewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println(listVewModel.getTest())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)


        fab.setOnClickListener {
            val action = TaskListFragmentDirections.actionNavTasksToNavNewTask(null)
            findNavController().navigate(action)
        }


        // Set the adapter
        if (recyclerView != null) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = TaskRecyclerViewAdapter(
                    db.tasksDao,
                    this@TaskListFragment::callBack
                )
                (layoutManager as LinearLayoutManager).scrollToPosition(Int.MAX_VALUE / 2)
                PagerSnapHelper().attachToRecyclerView(this)
            }
        }



        return view
    }

    private fun callBack(item: CalendarTask) {
        Timber.i("clicked item : ${item.getTitle()}")
        val action = TaskListFragmentDirections.actionNavTasksToNavNewTask(item as Task)
        findNavController().navigate(action)

    }
}
