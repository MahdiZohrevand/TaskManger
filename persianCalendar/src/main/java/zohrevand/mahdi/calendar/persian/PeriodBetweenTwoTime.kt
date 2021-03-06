package zohrevand.mahdi.calendar.persian

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.*
import kotlin.math.abs

class PeriodBetweenTwoTime {

    private var _lastDay = 0
    private var nowPersian = PersianDate()
    private val formatter = PersianDateFormat("y/m/d")

    //in the real word we must get it from storage
    public val birthPersianDate = PersianDate().apply {
        shYear = 1369
        shMonth = 4
        shDay = 3
    }

    public fun getPeriodBetweenNowAndBirthDay(
        end: PersianDate, callBack: (
            year: Int,
            month: Int,
            day: Int
        ) -> Unit
    ) {
        getPeriodBetweenPersianDates(birthPersianDate, end, callBack)
    }


    public fun getPeriodBetweenPersianDates(
        start: PersianDate,
        end: PersianDate,
        callBack: (
            year: Int,
            month: Int,
            day: Int
        ) -> Unit
    ) {
        val formatter = PersianDateFormat("y/m/d")
        val startMonth = start.shMonth
        val endMonth = end.shMonth
        val startDay = start.shDay
        val endDay = end.shDay
        var afterStartDayAndMonth = false
        if (endMonth > startMonth) {
            afterStartDayAndMonth = true
        } else if (endMonth == startMonth && endDay >= startDay) {
            afterStartDayAndMonth = true
        }
        val afterStartDay = endDay > startDay
        //===========================year
        val ageYear = if (afterStartDayAndMonth) {
            end.shYear - start.shYear
        } else {
            (end.shYear - start.shYear) - 1
        }
        //===========================month
        val ageMonth = when {
            endMonth > startMonth -> {
                if (afterStartDay) {
                    endMonth - startMonth
                } else {
                    (endMonth - startMonth) - 1
                }
            }
            endMonth < startMonth -> {

                if (afterStartDay) {
                    (endMonth + 12) - startMonth
                } else {
                    ((endMonth + 12) - startMonth) - 1
                }

            }
            else -> {
                if (afterStartDay) {
                    0
                } else {
                    11
                }
            }
        }

        //==========================days
        val ageDays =
            if (afterStartDay) {
                end.shDay - start.shDay
            } else {
                end.monthLength - abs(end.shDay - start.shDay)
            }
        callBack(ageYear, ageMonth, ageDays)
    }

    /**
     * get Persian Date for given value
     */
    public fun getPersianDate(day: Int): PersianDate? {
        /* val realDay = day - _lastDay
         _lastDay = day*/
        nowPersian = PersianDate()
        return nowPersian.addDay(day.toLong())
    }

    public fun getPersianDateSimpleFormat(day: Int): String {
        return formatter.format(getPersianDate(day))
    }

    public fun getDateAndAge(
        day: Int,
        dateCallBack: (date: String) -> Unit,
        ageCallBack: (year: Int, month: Int, day: Int) -> Unit
    ) {
        val nowPersian = getPersianDate(day)
        val date = formatter.format(nowPersian)
        getPeriodBetweenNowAndBirthDay(nowPersian!!) { year, month, _day ->
            ageCallBack(year, month, _day)
            dateCallBack(date)
        }
    }

    public fun convertDayPositionToMilliSecondDate(day: Int): List<Long> {
        val start = getPersianDate(day)?.setHour(0)?.setMinute(0)?.setSecond(0)?.time
        val end = getPersianDate(day)?.setHour(23)?.setMinute(59)?.setSecond(59)?.toDate()?.time
        return listOf<Long>(start ?: -1, end ?: -1)
    }

    public fun convertDayPositionToDate(day: Int): List<Date> {
        val start = getPersianDate(day)?.setHour(0)?.setMinute(0)?.setSecond(0)?.toDate()
        val end = getPersianDate(day)?.setHour(23)?.setMinute(59)?.setSecond(59)?.toDate()
        return listOf<Date>(start!!, end!!)
    }
}