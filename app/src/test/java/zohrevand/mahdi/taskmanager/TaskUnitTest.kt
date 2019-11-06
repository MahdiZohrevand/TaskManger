package zohrevand.mahdi.taskmanager


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import zohrevand.mahdi.taskmanager.business.StopWatch
import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.business.TaskLogger
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TaskUnitTest {

    private val task = Task("test", "description")

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }


    val lifecycle = LifecycleRegistry(lifecycleOwner)

    @Test
    fun task_IsCorrect() {
        assertEquals(
            true,
            task.title.equals("test", true) && task.description.equals("description", true)
        )
        println(task.title + " " + task.description)

    }

    @Test
    fun task_getCreateTime() {
        val dateFormat = SimpleDateFormat()
        val date = Date(task.createDate)
        println(dateFormat.format(date))

    }


    @Test
    fun task_spannedTime() {
        val taskLogger = TaskLogger(task, StopWatch())
        taskLogger.startTask()
        Thread.sleep(10000)
        taskLogger.stopTask()
        assertEquals(Duration.ofMillis(task.spannedTime).seconds, 10)
    }


}
