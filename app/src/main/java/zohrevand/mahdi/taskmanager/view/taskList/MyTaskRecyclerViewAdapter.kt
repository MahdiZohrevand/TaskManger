package zohrevand.mahdi.taskmanager.view.taskList

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import zohrevand.mahdi.taskmanager.R


//import zohrevand.mahdi.taskmanager.view.taskList.TaskListFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.row_task_item.view.*
import zohrevand.mahdi.dayview.view.DayView
import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.business.getCreateDate

/**
 * [RecyclerView.Adapter] that can display a [Task] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 *
 */
class MyTaskRecyclerViewAdapter(
    private val mValues: List<Task>
  //  private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>() {

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_day_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
       val dayView = mView.findViewById<DayView>(R.id.day_view)


        override fun toString(): String {
            return super.toString()
        }
    }
}
