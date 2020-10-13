package zohrevand.mahdi.taskmanager.view.taskList


//import zohrevand.mahdi.taskmanager.view.taskList.TaskListFragment.OnListFragmentInteractionListener

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import zohrevand.mahdi.calendar.persian.PeriodBetweenTwoTime
import zohrevand.mahdi.customviewtest.model.CalendarTask
import zohrevand.mahdi.taskmanager.dataAccess.TasksDao
import zohrevand.mahdi.taskmanager.dataAccess.convertDbTaskListToDayViewTaskList
import zohrevand.mahdi.taskmanager.databinding.RowDayViewBinding



private const val TodayPosition = Int.MAX_VALUE / 2

class TaskRecyclerViewAdapter(
    private val tasksDao: TasksDao,
    private val callBack: (item: CalendarTask) -> Unit,
    private val goToPositionCallBack: ((position: Int) -> Unit)?,
    private val onDateClickCallBack: (() -> Unit)?
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    private val period = PeriodBetweenTwoTime()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, callBack, goToPositionCallBack, onDateClickCallBack)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var date = ""
        var age = ""


        if (position == TodayPosition) holder.binding.goToday.visibility =
            View.GONE else holder.binding.goToday.visibility = View.VISIBLE
        period.getDateAndAge(positionManager(position)
            , {
                date = it
            }, { year, month, day ->
                age = "$year/$month/$day"
            })
        holder.bind(date, age, getTasksForPosition(position, holder.binding))
    }

   private fun getTasksForPosition(
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

    class ViewHolder(
        val binding: RowDayViewBinding,
        callBack: (item: CalendarTask) -> Unit,
        goToPositionCallBack: ((position: Int) -> Unit)?,
        onDateClickCallBack: (() -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.dayView.setOnItemClickListener {
                callBack(it)
            }

            binding.goToday.setOnClickListener {
                goToPositionCallBack?.invoke(TodayPosition)
            }

            binding.dayViewDate.setOnClickListener {
                onDateClickCallBack?.invoke()
            }
        }


        fun bind(

            date: String,
            age: String,
            tasks: List<zohrevand.mahdi.customviewtest.model.Task>
        ) {

            binding.age = age
            binding.date = date
            binding.tasks = tasks
            binding.executePendingBindings()
        }

        companion object {
            fun from(
                parent: ViewGroup,
                callBack: (item: CalendarTask) -> Unit,
                goToPositionCallBack: ((position: Int) -> Unit)?,
                onDateClickCallBack: (() -> Unit)?
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowDayViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, callBack, goToPositionCallBack, onDateClickCallBack)
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


