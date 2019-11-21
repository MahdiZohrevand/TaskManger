package zohrevand.mahdi.taskmanager.utils

import java.util.*

private val useIt = 1


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
    val birthDate = Calendar.getInstance()
    birthDate.timeInMillis = birthDateInMillSec
    val persianCalendar = PersianCalendar.getPersianCalendar(birthDate)
    val rightNow = Calendar.getInstance()
    val diff = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)
    val yearPosition = diff.years
    val monthPosition = diff.months - 1
    val dayPosition = persianCalendar.get(Calendar.DAY_OF_MONTH) - 1
    val result = "${diff.years} سال ${diff.months} ماه ${diff.days} روز "
    spinnerPosition(yearPosition, monthPosition, dayPosition, result)
}

/**
 * calculate age from Persian date
 * @param year user birth date year in Shamsi like : 1364
 * @param month user birth date month in Shamsi 1..12
 * @param day user birth date day in Shamsi 1..31
 */
fun calculateAge(year: Int, month: Int, day: Int): String {
    val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
    val rightNow = Calendar.getInstance()
    val diff = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)
    return "${diff.years} سال ${diff.months} ماه ${diff.days} روز "
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


