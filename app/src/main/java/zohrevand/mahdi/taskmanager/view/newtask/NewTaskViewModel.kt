package zohrevand.mahdi.taskmanager.view.newtask

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import saman.zamani.persiandate.PersianDate
import zohrevand.mahdi.customviewtest.model.Task
import zohrevand.mahdi.taskmanager.NavigationCommand
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.TaskModel
import zohrevand.mahdi.taskmanager.utils.SingleLiveEvent
import java.util.*

class NewTaskViewModel(
    val db: TaskManagerDatabase,
    val androidContext: Context
) : ViewModel() {

    val navigation = SingleLiveEvent<NavigationCommand>()

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    private var isEditMode = false
    private lateinit var task: Task

    //===================================time
    private val calendar = Calendar.getInstance()
    private var pCalendar = PersianDate(calendar.time)


    var startHourPosition = calendar.get(Calendar.HOUR_OF_DAY)
    var startMinutePosition = calendar.get(Calendar.MINUTE)

    //for end time now hour + 1
    var endHourPosition = calendar.apply { add(Calendar.HOUR_OF_DAY, 1) }.get(Calendar.HOUR_OF_DAY)
    var endMinutePosition = startMinutePosition

    var yearPosition = pCalendar.shYear
    var monthPosition = pCalendar.shMonth - 1
    var dayPosition = pCalendar.shDay - 1

    //coroutine and its job+scope
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onSubmitClick() {
        uiScope.launch {
            insertNewTaskDb()
        }

    }


    private suspend fun insertNewTaskDb() {
        withContext(Dispatchers.IO) {
            val taskDao = db.tasksDao
            val startDate = getStartDate()
            val finishDate = getFinishDate()
            if (startDate < finishDate) {
                val title = title.value ?: ""
                val description = description.value ?: ""
                if (isEditMode) {
                    taskDao.update(
                        TaskModel(
                            TaskId = task._id,
                            title = title,
                            description = description,
                            startDate = startDate,
                            finishDate = finishDate
                        )
                    )
                } else {
                    taskDao.insert(
                        TaskModel(
                            title = title,
                            description = description,
                            startDate = startDate,
                            finishDate = finishDate
                        )
                    )

                }
                navigation.postValue(NavigationCommand.Back)
            } else {
                showError(androidContext.getString(R.string.start_bigger_than_finish))
            }
        }
    }

    private fun showError(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                androidContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }


    }


    private fun getFinishDate(): Date {
        pCalendar.shYear = yearPosition
        pCalendar.shMonth = monthPosition + 1
        pCalendar.shDay = dayPosition + 1
        pCalendar.hour = endHourPosition
        pCalendar.minute = endMinutePosition
        return pCalendar.toDate()

        /* calendar.set(Calendar.HOUR_OF_DAY, endHourPosition)
         calendar.set(Calendar.MINUTE, endMinutePosition)
         //  Log.d("endHourTime ", "${calendar.get(Calendar.HOUR_OF_DAY)} , ${calendar.get(Calendar.MINUTE)} , ${calendar.time.time}")
         return Date(calendar.timeInMillis)*/
    }


    private fun getStartDate(): Date {
        pCalendar.shYear = yearPosition
        pCalendar.shMonth = monthPosition + 1
        pCalendar.shDay = dayPosition + 1
        pCalendar.hour = startHourPosition
        pCalendar.minute = startMinutePosition
        return pCalendar.toDate()

        /* calendar.set(Calendar.HOUR_OF_DAY, startHourPosition)
         calendar.set(Calendar.MINUTE, startMinutePosition)
         //  Log.d("startHourTime ", "${calendar.get(Calendar.HOUR_OF_DAY)} , ${calendar.get(Calendar.MINUTE)} , ${calendar.time.time}")
         return Date(calendar.timeInMillis)*/
    }


    fun setTask(task: Task) {
        isEditMode = true
        this.task = task
        pCalendar = PersianDate(task.startDate)
        yearPosition = pCalendar.shYear
        monthPosition = pCalendar.shMonth - 1
        dayPosition = pCalendar.shDay - 1
        title.value = task._tittle
        description.value = task._description
        startHourPosition = task._startTimeHour.toInt()
        startMinutePosition = task._startTimeMinute.toInt()
        endHourPosition = task._endTimeHour.toInt()
        endMinutePosition = task._endTimeMinute.toInt()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun deleteTask() {
        db.tasksDao.deleteById(task._id)
    }
}