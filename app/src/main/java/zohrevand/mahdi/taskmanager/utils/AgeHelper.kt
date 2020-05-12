package zohrevand.mahdi.taskmanager.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import zohrevand.mahdi.calendar.persian.PersianCalendar
import java.lang.Exception
import java.util.*

/**
 * calculate age form birth date in millisecond
 * @param birthDateInMillSec user birth date in millisecond
 * @param spinnerPosition call back for adapter position
 */
fun calculateAge(
    birthDateInMillSec: Long, spinnerPosition: (
        year: Int, month: Int, day: Int
        , age: String
    ) -> Unit
) {
    val birthDate = Calendar.getInstance().apply { timeInMillis = birthDateInMillSec }
    val persianCalendar = PersianCalendar(birthDate)
    val currentPersianYear = PersianCalendar.getPersianYear(Calendar.getInstance().time)
    val yearPosition = currentPersianYear - persianCalendar.year
    val monthPosition = persianCalendar.month - 1
    val dayPosition = persianCalendar.day - 1
    val ageString =
        calculatePeriod(persianCalendar.year, persianCalendar.month, persianCalendar.day)
    spinnerPosition(yearPosition, monthPosition, dayPosition, ageString)
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


    val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
    val now = LocalDate.now()
    val localBirth = LocalDate.of(
        birthDate.get(Calendar.YEAR),
        birthDate.get(Calendar.MONTH) + 1,
        birthDate.get(Calendar.DAY_OF_MONTH)
    )
    val period = Period.between(localBirth, now)

    return "${period.years} سال ${period.months} ماه ${period.days} روز "
}


/**
 * convert Persian date to millisecond
 * @param year user birth date year in Shamsi like : 1364
 * @param month user birth date month in Shamsi 1..12
 * @param day user birth date day in Shamsi 1..31
 * @return date in millisecond
 */
fun convertPersianDateToMillisecond(year: Int, month: Int, day: Int): Long {
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
