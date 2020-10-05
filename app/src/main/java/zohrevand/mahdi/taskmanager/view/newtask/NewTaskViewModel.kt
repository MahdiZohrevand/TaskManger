package zohrevand.mahdi.taskmanager.view.newtask

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.TaskModel
import java.util.*

class NewTaskViewModel(
    val db: TaskManagerDatabase,
    val androidContext: Context
) : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    val taskMutableLiveData = db.tasksDao.getAllTask()

    //===================================time
    private val calendar = Calendar.getInstance()
    var startHourPosition = calendar.get(Calendar.HOUR_OF_DAY)
    var startMinutePosition = calendar.get(Calendar.MINUTE)

    //for end time now hour + 1
    var endHourPosition = calendar.apply { add(Calendar.HOUR_OF_DAY, 1) }.get(Calendar.HOUR_OF_DAY)
    var endMinutePosition = startMinutePosition


    //coroutine and its job+scope
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /*init {
        title.value = "test"
        taskMutableLiveData.observeForever {
            val s = it
        }

        uiScope.launch {

        }
        Log.d("endHour","$endHourPosition")
    }*/

    fun onSubmitClick() {
        uiScope.launch {
            insertNewTaskDb()
        }
    }


    private suspend fun insertNewTaskDb() {
        withContext(Dispatchers.IO) {
            val taskDao = db.tasksDao
           // val start = getStartMillisecond()
          //  val end = getEndMillisecond()
            val startDate = getStartDate()
            val finishDate = getFinishDate()
            if (startDate < finishDate) {
                val title = title.value
                val description = description.value ?: ""
                if (title != null && title.isNotEmpty()) {
                    taskDao.insert(
                        TaskModel(
                            title = title,
                            description = description,
                            startDate = startDate,
                            finishDate = finishDate
                        )
                    )
                } else {
                    showError()
                }

                Log.d("titleAndDescription", "${title} , ${description}")
                // Log.d("start and end","$end , $start")
            } else {
                showError()
            }
        }
    }

    private fun showError() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                androidContext,
                "end time must be bigger than start time",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun getEndMillisecond(): Long {
        calendar.set(Calendar.HOUR_OF_DAY, endHourPosition)
        calendar.set(Calendar.MINUTE, endMinutePosition)
        //  Log.d("endHourTime ", "${calendar.get(Calendar.HOUR_OF_DAY)} , ${calendar.get(Calendar.MINUTE)} , ${calendar.time.time}")
        return calendar.timeInMillis

    }

    private fun getFinishDate(): Date {
        calendar.set(Calendar.HOUR_OF_DAY, endHourPosition)
        calendar.set(Calendar.MINUTE, endMinutePosition)
        //  Log.d("endHourTime ", "${calendar.get(Calendar.HOUR_OF_DAY)} , ${calendar.get(Calendar.MINUTE)} , ${calendar.time.time}")
        return Date(calendar.timeInMillis)
    }

    private fun getStartMillisecond(): Long {
        calendar.set(Calendar.HOUR_OF_DAY, startHourPosition)
        calendar.set(Calendar.MINUTE, startMinutePosition)
        //  Log.d("startHourTime ", "${calendar.get(Calendar.HOUR_OF_DAY)} , ${calendar.get(Calendar.MINUTE)} , ${calendar.time.time}")
        return calendar.timeInMillis
    }

    private fun getStartDate(): Date {
        calendar.set(Calendar.HOUR_OF_DAY, startHourPosition)
        calendar.set(Calendar.MINUTE, startMinutePosition)
        //  Log.d("startHourTime ", "${calendar.get(Calendar.HOUR_OF_DAY)} , ${calendar.get(Calendar.MINUTE)} , ${calendar.time.time}")
        return Date(calendar.timeInMillis)
    }


    suspend fun getAllTask() {
        withContext(Dispatchers.IO) {
            val tasksDao = db.tasksDao
            tasksDao.getAllTask().observeForever {
                val x = it

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}