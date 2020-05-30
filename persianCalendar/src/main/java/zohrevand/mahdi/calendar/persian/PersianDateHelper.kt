package zohrevand.mahdi.calendar.persian

import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class PersianDateHelper {

    private val birthCalendar = PersianCalendar.getGregorianCalendar(1369, 4, 3)
    private val xCalendar = Calendar.getInstance()
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

    public fun getPersianDateAndAge(
        day: Int,
        dateAndAge: (persianDate: String, period: Period) -> Unit
    ) {
        //shown date
        date.time = System.currentTimeMillis() + (dayInMilliSecond * day)
     //   Calendar.getInstance().set(Calendar.DATE,date)
        dateAndAge(
            PersianCalendar.getPersianDate(date),
            PersianCalendar.getPeriodOfTwoTime(
                birthCalendar,
                xCalendar.apply { timeInMillis = System.currentTimeMillis() + (dayInMilliSecond * day) })
        )
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



    /**
     * calculate period time between of now and given date
     * you have to call  AndroidThreeTen.init(context)
     * before use,i made call in application class
     * @param year user birth date year in Shamsi like : 1364
     * @param month user birth date month in Shamsi 1..12
     * @param day user birth date day in Shamsi 1..31
     *
     * @return string format of elapsed date in persian date format
     */
    fun calculatePeriod(year: Int, month: Int, day: Int): String {

        //check given date is correct
        try {

            //because PersianCalendar.isLeapYear() works correctly for years between 1343 and 1472 we assume
            //years before 1343 are leap year
            val isLeap: Boolean = if (year < 1343) {
                true
            } else {
                PersianCalendar.isLeapYear(year)
            }
            monthChecker(month, day, isLeap)
        } catch (e: MonthCheckerException) {
            return "${e.message}"
        }

        //TODO : convert it to global
        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)

        val endDate = LocalDate.now()

        val startDate = LocalDate.of(
            birthDate.get(Calendar.YEAR),
            birthDate.get(Calendar.MONTH) + 1,
            birthDate.get(Calendar.DAY_OF_MONTH)
        )
        val period = Period.between(startDate, endDate)

        return "${period.years} سال ${period.months} ماه ${period.days} روز "
    }


    private fun localDateManager(day : Int){
        when (day) {
            0 -> {
               //return LocalDate.now()
            }
            in 1..Int.MAX_VALUE -> {
                //return LocalDate + day
                //return dayAfterToday(day)
            }
            in -1 downTo Int.MIN_VALUE -> {
               // return dayBeforeToday(day)
            }
        }
    }

    /**
     * convert Persian date to millisecond
     * @param year user birth date year in Shamsi like : 1364
     * @param month user birth date month in Shamsi 1..12
     * @param day user birth date day in Shamsi 1..31
     * @return date in millisecond
     */
    public fun convertPersianDateToMillisecond(year: Int, month: Int, day: Int): Long {
        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
        return birthDate.timeInMillis
    }


    /**
     * check this month's max day and leap year
     */
    @Throws(MonthCheckerException::class)
    fun monthChecker(month: Int, day: Int, isLeap: Boolean) {
        when {
            month > 6 && day > 30 -> throw(MonthCheckerException("این ماه ۳۱ روزه نیست"))
            month == 12 && !isLeap && day > 29 -> throw(MonthCheckerException("این سال کبیسه نیست"))
        }
    }

    class MonthCheckerException(message: String?) : Exception(message)


}