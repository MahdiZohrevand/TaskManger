package zohrevand.mahdi.taskmanager.business

import java.lang.Exception

class TaskLogger(private val task: Task, private val stopWatch: StopWatch) {


    fun startTask() {
        stopWatch.start()
    }


    /**
     * stop timer and save spanned time in task
     * if you did not start task and call this method it throw exception
     */
    fun stopTask() {
        val spannedTime = stopWatch.stopAndGetSpannedTime()
        if (spannedTime == null) {
            throw TaskNotStartedException("you must start task before stop it")
        } else {
            task.spannedTime = spannedTime
        }
    }


    /**
     * save task in storage if exist update it otherwise crete new one
     * if save was success it return true otherwise return false
     */
    fun saveTask(): Boolean {
        return false
    }


    /**
     * exception class that demonstrate task not started
     */
    class TaskNotStartedException(message: String?) : Exception(message)

}
