package zohrevand.mahdi.taskmanager.view.taskList

import androidx.lifecycle.ViewModel
import zohrevand.mahdi.taskmanager.business.StopWatch
import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.business.TaskLogger
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase

class TaskListVewModel(db : TaskManagerDatabase) : ViewModel() {



    fun getTest(): String {
        return "test"
    }

    fun startTimer() {

    }

    fun startTimer(task: Task) {
        val taskLogger = TaskLogger(task , StopWatch())
        taskLogger.startTask()
    }


}