package zohrevand.mahdi.taskmanager.business

import android.app.ActivityManager
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import zohrevand.mahdi.calendar.persian.PersianCalendar
import java.util.*
import java.util.concurrent.TimeUnit

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

    fun getSpannedTime() = millisecondToSpannedTimeConvert(spannedTime)

    fun millisecondToSpannedTimeConvert(millis: Long): String {
        var time = millis
        val hours = TimeUnit.MILLISECONDS.toHours(time)
        time -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        time -= TimeUnit.MINUTES.toMillis(minutes)


        val sb = StringBuilder(64)
        sb.append(hours)
        if (minutes > 10) sb.append(":") else sb.append(":0")
        sb.append(minutes)

        return sb.toString()
    }


}

fun Task.getCreateDate(): String {
    val calendar = PersianCalendar(Calendar.getInstance().apply { timeInMillis = createDate })
    return "${calendar.year}/${calendar.month}/${calendar.day}"
}

