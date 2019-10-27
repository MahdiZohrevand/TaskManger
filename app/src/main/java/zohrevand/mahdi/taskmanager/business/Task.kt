package zohrevand.mahdi.taskmanager.business

import android.app.ActivityManager
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Task(
    val title: String,
    val description: String,
    //all date and time are in millisecond
    val taskId: Long = -1,
    var finishDate: Long = -1,
    var deadLine: Long = -1,
    var spannedTime: Long = -1,
    var isDone: Boolean = false,
    val createDate: Long = Calendar.getInstance().timeInMillis
) : Parcelable {


    override fun toString(): String {
        return "$title $description"
    }

    fun addSppanedTime(sappanedTime: Long) {
        spannedTime += spannedTime
    }


}

