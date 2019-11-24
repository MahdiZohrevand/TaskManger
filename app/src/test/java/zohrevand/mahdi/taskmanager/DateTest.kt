package zohrevand.mahdi.taskmanager

import androidx.test.filters.SmallTest
import org.junit.Test
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@SmallTest
class DateTest {
    private val year = 1369
    private val month = 4
    private val day = 3

    @Test
    fun twoDateDifference() {
        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
        val rightNow = Calendar.getInstance()
        val period = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)

        period.apply {
            println(years)
            println(months)
            println(days)
        }
    }


    @Test
    fun millisecondTest() {
        val minute = minuteToMilli(61)
        val hour = hourToMilli(48)

        println(millisecondToSpannedTimeConvert(0))
        //   println(simpleFormat(hour + minute))
    }

    private fun minuteToMilli(minute: Long) =
        TimeUnit.MINUTES.toMillis(minute)


    private fun hourToMilli(hour: Long) =
        TimeUnit.HOURS.toMillis(hour)

    private fun dayToMilli(day: Long) =
        TimeUnit.DAYS.toMillis(day)
    

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