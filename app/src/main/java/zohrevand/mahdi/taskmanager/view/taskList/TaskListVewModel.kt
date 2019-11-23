package zohrevand.mahdi.taskmanager.view.taskListpragmatic

import androidx.lifecycle.ViewModel
import zohrevand.mahdi.taskmanager.business.StopWatch
import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.business.TaskLogger

class TaskListVewModel : ViewModel() {



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