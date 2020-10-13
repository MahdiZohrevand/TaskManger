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
import saman.zamani.persiandate.PersianDate
import timber.log.Timber
import zohrevand.mahdi.customviewtest.model.CalendarTask
import zohrevand.mahdi.customviewtest.model.Task
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.view.datepicker.DatePickerFragment
import kotlin.math.abs

private const val TodayPosition = Int.MAX_VALUE / 2

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
                this@TaskListFragment::goToPositionCallBack,
                this@TaskListFragment::onDateClickCallBack
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
        val currentPosition =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val movement = abs(position - currentPosition)
        if (movement < 21) {
            recyclerView.smoothScrollToPosition(position)
        } else {
            recyclerView.scrollToPosition(position)
        }

    }

    private fun onDateClickCallBack() {
        val dialog = DatePickerFragment.newInstance()
        dialog.callBack = {
            val pCalendar = PersianDate(it).endOfDay()
            val pCalendarNow = PersianDate().endOfDay()
            if (pCalendarNow.compareTo(pCalendar) != 0) {
                val movement = pCalendar.getDayUntilToday(pCalendarNow)
                if (pCalendar.after(pCalendarNow)) goToPositionCallBack(((Int.MAX_VALUE / 2) + movement).toInt()) else goToPositionCallBack(
                    ((Int.MAX_VALUE / 2) - movement).toInt()
                )
            }
        }
        dialog.show(fragmentManager!!, null)
    }
}
