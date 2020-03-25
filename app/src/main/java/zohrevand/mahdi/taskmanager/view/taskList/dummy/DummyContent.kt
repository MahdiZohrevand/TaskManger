package zohrevand.mahdi.taskmanager.view.taskList.dummy

import org.threeten.bp.DateTimeUtils
import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Task> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Task> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: Task) {
        ITEMS.add(item)
        ITEM_MAP.put(item.taskId.toString(), item)
    }

    private fun createDummyItem(position: Int): Task {
        return Task(
            "عنوان" + position.toString(),
            "توضیحات" + position,
            position.toLong(),
            spannedTime = converMinuteToMillisecond(position.toLong()),
            createDate = getDayBeforToday(position)
        )
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }


    private fun converMinuteToMillisecond(minute: Long) =
        TimeUnit.MINUTES.toMillis(minute)

    private fun getDayBeforToday(before : Int): Long {
       val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,-before)
        return calendar.timeInMillis
    }
}
