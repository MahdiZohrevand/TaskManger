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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.viewmodel.ext.android.viewModel
import zohrevand.mahdi.customviewtest.model.Task
import zohrevand.mahdi.dayview.view.DayView
import zohrevand.mahdi.taskmanager.R

import zohrevand.mahdi.taskmanager.view.taskList.dummy.DummyContent

/**
 * A fragment representing a list of Task.
 * Activities containing this fragment MUST implement the
 * [TaskListFragment.OnListFragmentInteractionListener] interface.
 */
class TaskListFragment : Fragment() {



   // private var listener: OnListFragmentInteractionListener? = null

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
        //val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val dayView = view.findViewById<DayView>(R.id.day_view)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        val navController = findNavController()

        //float action button navigate to new task
        fab.setOnClickListener {
            navController.navigate(R.id.nav_new_task)
        }

        dayView.addCalendarTask(Task(_tittle = "test" , _startTimeHour = 1f , _startTimeMinute = 0f, _endTimeHour = 2f , _endTimeMinute = 0f ))

        // Set the adapter
       /* if (recyclerView != null) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyTaskRecyclerViewAdapter(DummyContent.ITEMS,
                    object : OnListFragmentInteractionListener {
                        override fun onListFragmentInteraction(item: Task) {
                            val action = TaskListFragmentDirections.actionNavTasksToNavTimer()
                            action.task = item
                            navController.navigate(action)

                        }

                    })
            }
        }*/



        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    /*    if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            // throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
   /* interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Task)
    }*/

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
