package zohrevand.mahdi.taskmanager.view.taskList

import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zohrevand.mahdi.calendar.persian.PeriodBetweenTwoTime
import zohrevand.mahdi.calendar.persian.PersianCalendar
import zohrevand.mahdi.calendar.persian.PersianDateHelper


//import zohrevand.mahdi.taskmanager.view.taskList.TaskListFragment.OnListFragmentInteractionListener

import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.databinding.RowDayViewBinding

/**
 * [RecyclerView.Adapter] that can display a [Task] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 *
 */
class MyTaskRecyclerViewAdapter(
    private val tasks: List<Task>
    //  private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>() {

    val period = PeriodBetweenTwoTime()


    val persianDateHelper = PersianDateHelper()

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Task
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            //  mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var title = ""
        var description = ""


        period.getDateAndAge(positionManager(position)
            , {
                title = it
            }, { year, month, day ->
                description = "$year/$month/$day"
            })





        holder.bind(tasks[1], title, description, getTasksForPosition(position))


    }

    fun getTasksForPosition(position: Int): List<zohrevand.mahdi.customviewtest.model.Task> =
        //we return dumy data
        listOf<zohrevand.mahdi.customviewtest.model.Task>(
            zohrevand.mahdi.customviewtest.model.Task(
                _tittle = "test",
                _startTimeHour = 10f,
                _startTimeMinute = 25f,
                _endTimeHour = 11f,
                _endTimeMinute = 20f,
                _rectColor = Color.parseColor("#e44285F4")
            )
        )


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
        Log.i("date position", "$position")
        when (position) {
            Int.MAX_VALUE / 2 -> {
                realPosition = 0
                Log.i("date", "$realPosition")
            }
            in 0..Int.MAX_VALUE / 2 -> {
                realPosition = (Int.MAX_VALUE / 2) - position
                Log.i("date after", "$realPosition")
            }
            in (Int.MAX_VALUE / 2)..Int.MAX_VALUE -> {
                realPosition = Int.MAX_VALUE / 2 - position
                Log.i("date before", "$realPosition")
            }
        }
        return realPosition
    }


}


