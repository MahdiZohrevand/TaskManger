package zohrevand.mahdi.taskmanager

import androidx.test.filters.SmallTest
import org.junit.Test
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import zohrevand.mahdi.taskmanager.view.taskList.dummy.DummyContent
import java.util.*


@SmallTest
class PersinaCalendarTest {

   private val items = DummyContent.ITEMS


    @Test
    fun persiaCalendarPersiaDate_test(){
        val calendar = PersianCalendar()
        calendar.setPersianCalendar(Calendar.getInstance().apply { timeInMillis = items[1].createDate }
        )
        println("${calendar.year}/${calendar.month}/${calendar.day}")
    }


}