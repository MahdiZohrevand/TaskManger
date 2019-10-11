package zohrevand.mahdi.taskmanager.business

import android.app.ActivityManager
import java.util.*

class Task(val title: String, val description: String) {

    val TaskId: Long = -1

    //all date and time are in millisecond
    var finishDate: Long = -1
    var deadLine: Long = -1
    var spannedTime: Long = -1
    var isDone: Boolean = false
    val createDate: Long = Calendar.getInstance().timeInMillis


    override fun toString(): String {
        return "$title $description"
    }

    fun addSppanedTime(sappanedTime: Long) {
        spannedTime += spannedTime
    }


}

