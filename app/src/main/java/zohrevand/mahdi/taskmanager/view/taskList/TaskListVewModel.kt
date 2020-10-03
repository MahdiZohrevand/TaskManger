package zohrevand.mahdi.taskmanager.view.taskList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import zohrevand.mahdi.taskmanager.business.StopWatch
import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.business.TaskLogger
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase

class TaskListVewModel(val db: TaskManagerDatabase) : ViewModel() {


    //coroutine and its job+scope
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getTest(): String {

        db.tasksDao.getAllTask().observeForever {
            val debug = it

        }
        return "test"
    }

    fun startTimer() {

    }

    fun startTimer(task: Task) {
        val taskLogger = TaskLogger(task, StopWatch())
        taskLogger.startTask()
    }


}