package zohrevand.mahdi.calendar.persian

import android.util.TimeUtils
import java.util.*
import java.util.concurrent.TimeUnit

class PersianDateHelper {


    private val dayInMilliSecond = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
    private val date = Date()

    /**
     * return persian date from today
     * @param day if day be zero it returns current date(today)
     * if day be positive it returns date after current date
     * if day be negative it returns day before current date
     *
     * @return string format of persian date
     * for example 99/3/22
     */
    public fun getPersianDate(day: Int): String {

        when (day) {
            0 -> {
                return currentDay()
            }
            in 1..Int.MAX_VALUE -> {
                return dayAfterToday(day)
            }
            in -1 downTo Int.MIN_VALUE -> {
                return dayBeforeToday(day)
            }
        }
        return ""
    }

    private fun dayBeforeToday(day: Int): String {
        date.time = System.currentTimeMillis() + (dayInMilliSecond * day)
        return PersianCalendar.getPersianDate(date)
    }

    private fun dayAfterToday(day: Int): String {
        date.time = System.currentTimeMillis() + (dayInMilliSecond * day)
        return PersianCalendar.getPersianDate(date)
    }

    private fun currentDay(): String {
        date.time = System.currentTimeMillis()
        return PersianCalendar.getPersianDate(date)
    }

}