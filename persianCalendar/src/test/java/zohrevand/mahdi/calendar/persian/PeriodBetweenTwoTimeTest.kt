package zohrevand.mahdi.calendar.persian

import org.junit.Test

import org.junit.Assert.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class PeriodBetweenTwoTimeTest {
    val period = PeriodBetweenTwoTime()
    val nowPersianDate = PersianDate()
    val formatter = PersianDateFormat("y/m/d")

    @Test
    fun getBirthPersianDate() {
    }

    @Test
    fun getPeriodBetweenNowAndBirthDay() {
        period.getPeriodBetweenNowAndBirthDay(nowPersianDate) { year, month, day ->
            println("$year , $month , $day")
        }

    }

    @Test
    fun getPeriodBetweenPersianDates() {
    }

    @Test
    fun getPersianDateTest(){
        println(formatter.format(period.getPersianDate(0)))
        println(formatter.format(period.getPersianDate(1)))
        println(formatter.format(period.getPersianDate(2)))
        println(formatter.format(period.getPersianDate(21)))
        println(formatter.format(period.getPersianDate(-20)))
    }

    @Test
    fun dateInMillisecondRang(){
       val result  = period.convertDayPositionToMilliSecondDate(0)
        print("${result.first()} , ${result.last()}")
    }
}