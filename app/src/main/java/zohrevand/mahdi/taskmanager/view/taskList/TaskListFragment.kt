package zohrevand.mahdi.taskmanager.view.taskList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import zohrevand.mahdi.customviewtest.model.CalendarTask
import zohrevand.mahdi.customviewtest.model.Task
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase


class TaskListFragment : Fragment() {


    val db: TaskManagerDatabase by inject()


    private val listVewModel: TaskListVewModel by viewModel()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println(listVewModel.getTest())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)


        fab.setOnClickListener {
            val action = TaskListFragmentDirections.actionNavTasksToNavNewTask(null)
            findNavController().navigate(action)
        }


        // Set the adapter
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = TaskRecyclerViewAdapter(
                db.tasksDao,
                this@TaskListFragment::onDayViewItemClickCallBack,
                this@TaskListFragment::goToPositionCallBack
            )
            (layoutManager as LinearLayoutManager).scrollToPosition(Int.MAX_VALUE / 2)
            PagerSnapHelper().attachToRecyclerView(this)
        }




        return view
    }

    private fun onDayViewItemClickCallBack(item: CalendarTask) {
        Timber.i("clicked item : ${item.getTitle()}")
        val action = TaskListFragmentDirections.actionNavTasksToNavNewTask(item as Task)
        findNavController().navigate(action)

    }

    private fun goToPositionCallBack(position: Int) {
        recyclerView.smoothScrollToPosition(Int.MAX_VALUE / 2)

    }
}
