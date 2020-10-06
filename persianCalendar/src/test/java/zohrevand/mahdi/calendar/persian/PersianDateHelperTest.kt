package zohrevand.mahdi.calendar.persian


import org.junit.Assert.assertEquals
import org.junit.Test
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.*
import kotlin.math.abs

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PersianDateHelperTest {

    val helper = PersianDateHelper()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun date_helper_test() {
        //get last day
        println(helper.getPersianDate(-1))
        //get today
        println(helper.getPersianDate(0))
        //get tomorrow
        println(helper.getPersianDate(1))
    }

    @Test
    fun epoch_day() {
        val birthDayInMilliSecond = helper.convertPersianDateToMillisecond(1369, 4, 3)
        // val startDate = LocalDate.ofEpochDay()
        // val date = LocalDate.ofEpochDay(birthDayInMilliSecond)
        //val period = Period.between(date, LocalDate.now())
        println(PersianCalendar.getPersianDate(Date(birthDayInMilliSecond)))
    }

    @Test
    fun period_between_two_date() {

        val calendarStart = PersianCalendar.getGregorianCalendar(1399, 2, 31)
        val calendarEnd = Calendar.getInstance()
        val period = PersianCalendar.getPeriodOfTwoTime(calendarStart, calendarEnd)
        println("${period.years} ${period.months} ${period.days}")
    }

    @Test
    fun persian_date_and_age() {
        helper.getPersianDateAndAge(0) { date, period ->
            println(date)
            println("${period.years} ${period.months} ${period.days}")
        }
    }

    @Test
    fun reproduce_bug() {

        val birthDayCalendar = PersianCalendar.getPersianCalendar(Calendar.getInstance().apply {
            set(Calendar.YEAR, 1990)
            set(Calendar.MONTH, 6)
            set(Calendar.DAY_OF_MONTH, 24)
        })
        // birthDayCalendar.add(Calendar.MONTH , 1)
        with(birthDayCalendar) {
            println("${get(Calendar.YEAR)} ${get(Calendar.MONTH)} ${get(Calendar.DAY_OF_MONTH)}")
        }

        val diroz = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -100) }
        val today = Calendar.getInstance()
        val period = PersianCalendar.getPeriodOfTwoTime(birthDayCalendar, today)

        period.apply {
            println("$years $months $days")
        }

        /*  //  for (i in -1 downTo -100)
          helper.getPersianDateAndAge(-22) { persianDate, period ->
              // println(i.toString())
              println(persianDate)
              period.apply {
                  println("$years $months $days")
              }

          }*/
    }


    @Test
    fun `test Persian Date`() {
        val birthCalendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 1990)
            set(Calendar.MONTH, 6)
            set(Calendar.DAY_OF_MONTH, 24)
        }
        val persianDate = PersianDate().apply {
            shYear = 1369
            shMonth = 4
            shDay = 3
        }

        val untilToday = persianDate.untilToday()

        val persianDateFormat = PersianDateFormat("y/m/d")
        val format = persianDateFormat.format(persianDate)
        println("${persianDate.grgYear} ${persianDate.grgMonth} ${persianDate.grgDay}")
        println("${untilToday[0]}")
    }

    @Test
    fun `test age with Persian date`() {
        val birthDay = PersianDate().apply {
            shYear = 1369
            shMonth = 4
            shDay = 3
        }

        val nowPersian = PersianDate()
        val formatter = PersianDateFormat("y/m/d")


        for (i in 1..8) {
            println("MonthNumber : $i")
            for (j in 1..5) {
                nowPersian.apply {
                    shMonth = i
                    shDay = j
                }
                getPeriodBetweenTwoDate(birthDay, nowPersian)
            }
        }
    }

    @Test
    fun `test persian calendar add day`(){
        val nowPersian = PersianDate()
        val formatter = PersianDateFormat("y/m/d")
        nowPersian.addDay(-100)
        println(formatter.format(nowPersian))

    }

    /**
     * test age for given day
     */
    @Test
    fun `test age for given day`(){
        val birthDay = PersianDate().apply {
            shYear = 1369
            shMonth = 4
            shDay = 15
        }
        val nowPersian = PersianDate()
        val formatter = PersianDateFormat("y/m/d")
        for(i in 0 .. 35){
            nowPersian.addDay(1)
            getPeriodBetweenTwoDate(birthDay , nowPersian)
        }
    }


    private fun getPeriodBetweenTwoDate(start: PersianDate, end: PersianDate) {
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

        //=========================print
        println(formatter.format(end))
        println("$ageYear $ageMonth ${abs(ageDays)}")
        println("------------------------------------")

        //====================================asserts
        if (startMonth == 3 && startDay == 1) {
            assertEquals("after birth day must increase month number", 11, ageMonth)
        }


        if (startMonth == 3 && startDay == 4) {
            assertEquals(12, ageMonth)
        }

        /*  if (startMonth == endMonth && startDay == endDay) {
              assertEquals(0, ageMonth)
              assertEquals(0, ageDays)
          }*/
    }
}