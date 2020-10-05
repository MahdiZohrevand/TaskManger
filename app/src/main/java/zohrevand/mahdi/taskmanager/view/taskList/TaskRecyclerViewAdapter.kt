package zohrevand.mahdi.taskmanager.view.taskList

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber
import zohrevand.mahdi.calendar.persian.PeriodBetweenTwoTime


//import zohrevand.mahdi.taskmanager.view.taskList.TaskListFragment.OnListFragmentInteractionListener

import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.dataAccess.TasksDao
import zohrevand.mahdi.taskmanager.dataAccess.convertDbTaskListToDayViewTaskList
import zohrevand.mahdi.taskmanager.databinding.RowDayViewBinding

/**
 * [RecyclerView.Adapter] that can display a [Task] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 *
 */
class TaskRecyclerViewAdapter(
    private val tasks: List<Task>,
    private val tasksDao: TasksDao
    //  private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    val period = PeriodBetweenTwoTime()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Task
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var date = ""
        var age = ""
        period.getDateAndAge(positionManager(position)
            , {
                date = it
            }, { year, month, day ->
                age = "$year/$month/$day"
            })


        holder.bind(tasks[1], date, age, getTasksForPosition(position, holder.binding))


    }

    fun getTasksForPosition(
        position: Int,
        binding: RowDayViewBinding
    ): List<zohrevand.mahdi.customviewtest.model.Task> {

        var taskList: List<zohrevand.mahdi.customviewtest.model.Task> = listOf()
        val realPosition = positionManager(position)
        val date = period.convertDayPositionToDate(realPosition)
        tasksDao.getTaskForDay(date[0], date[1]).observeForever {
            Timber.i("${it.size}")
            taskList = convertDbTaskListToDayViewTaskList(it)
            binding.dayView.setCalendarTask(taskList)
        }
        return taskList
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    class ViewHolder(val binding: RowDayViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Task,
            date: String,
            age: String,
            tasks: List<zohrevand.mahdi.customviewtest.model.Task>
        ) {
            binding.task = item
            binding.age = age
            binding.date = date
            binding.tasks = tasks
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowDayViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    /**
     * @return
     */
    private fun positionManager(position: Int): Int {
        var realPosition = 0
        Timber.i("date position : $position")
        when (position) {
            Int.MAX_VALUE / 2 -> {
                realPosition = 0
                Timber.i("date : $realPosition")
            }
            in 0..(Int.MAX_VALUE / 2) -> {
                realPosition = (Int.MAX_VALUE / 2) - position
                Timber.i("date after : $realPosition")
            }
            in (Int.MAX_VALUE / 2)..Int.MAX_VALUE -> {
                realPosition = Int.MAX_VALUE / 2 - position
                Timber.i("date before : $realPosition")
            }
        }
        return realPosition
    }


}


