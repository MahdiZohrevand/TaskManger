package zohrevand.mahdi.taskmanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import zohrevand.mahdi.calendar.persian.PersianCalendar
import java.util.*
import java.util.concurrent.TimeUnit


@SmallTest
@RunWith(AndroidJUnit4::class)
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
    fun getPeriod() {

        val instrumentationContext = InstrumentationRegistry.getInstrumentation().context

        AndroidThreeTen.init(instrumentationContext)

        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
        val now = LocalDate.now()
        val localBirth = LocalDate.of(
            birthDate.get(Calendar.YEAR),
            birthDate.get(Calendar.MONTH) + 1,
            birthDate.get(Calendar.DAY_OF_MONTH)
        )

        print("birth date  ${birthDate.get(Calendar.YEAR)} , ${birthDate.get(Calendar.MONTH)} , ${birthDate.get(Calendar.DAY_OF_MONTH)} \n")
        print("now date  ${now.year} , ${now.month} , ${now.dayOfMonth} \n")
        print("birth date  ${localBirth.year} , ${localBirth.month} , ${localBirth.dayOfMonth} \n")


        val period = Period.between(now, localBirth)



        println(period.days)
        println(period.months)
        println(period.years)
    }


    @Test
    fun millisecondTest() {
        val minute = minuteToMilli(61)
        val hour = hourToMilli(48)

        println(millisecondToSpannedTimeConvert(0))
        //   println(simpleFormat(hour + minute))
    }

    @Test
    fun getDayAgo_test() {
        val date = getDayBeforToday(4)
        val calendar =
            PersianCalendar(Calendar.getInstance().apply { timeInMillis = date.timeInMillis })
        println("${calendar.year}/${calendar.month}/${calendar.day}")
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


    private fun getDayBeforToday(before: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -before)
        return calendar
    }

}