package zohrevand.mahdi.taskmanager

import androidx.test.filters.SmallTest
import org.junit.Test
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import java.util.*

@SmallTest
class DateTest {
    private val year = 1369
    private val month = 4
    private val day = 3

    @Test
    fun twoDateDifference() {
        Date()

        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
        val rightNow = Calendar.getInstance()
        val period = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)

        period.apply {
            println(years)
            println(months)
            println(days)
        }
    }

}