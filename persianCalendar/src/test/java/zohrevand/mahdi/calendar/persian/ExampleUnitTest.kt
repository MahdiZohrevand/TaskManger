package zohrevand.mahdi.calendar.persian

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

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


}