package zohrevand.mahdi.taskmanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import org.hamcrest.CoreMatchers.*
import org.junit.*


import org.junit.runner.RunWith

import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.TaskModel
import zohrevand.mahdi.taskmanager.dataAccess.TasksDao
import java.io.IOException
import java.lang.Exception
import org.hamcrest.MatcherAssert.assertThat

import zohrevand.mahdi.taskmanager.business.Task
import zohrevand.mahdi.taskmanager.LiveDataTestUtil.getValue

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ExampleInstrumentedTest {

  /*  private lateinit var tasksDao: TasksDao
    private lateinit var db: TaskManagerDatabase

    private lateinit var taskList: List<TaskModel>

   *//* //Set the main coroutines dispatcher for unit testing
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = *//*



    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun createDb() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, TaskManagerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        tasksDao = db.tasksDao

        taskList = listOf(
            TaskModel(title = "1", description = "1"),
            TaskModel(title = "2", description = "2"),
            TaskModel(title = "3", description = "3")
        )
    }


    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetTask() {

        val taskModel =
            TaskModel(title = "test", description = "description", spannedTimeMilli = 7000L)

        tasksDao.insert(taskModel)

        //data base has only one item
        val loadedTask = getValue(tasksDao.getAllTask())[0]

        //loaded data contains excepted values
        assertThat<TaskModel>(loadedTask, notNullValue())
        assertThat(loadedTask.title, `is`("test"))
        assertThat(loadedTask.description, `is`("description"))
        assertThat(loadedTask.spannedTimeMilli, `is`(7000L))
    }

    @Test
    fun insertTaskAndDelete() {
        val taskModel = TaskModel(
            title = "must be deleted",
            description = "description",
            spannedTimeMilli = 7000L
        )
        db.tasksDao.insert(taskModel)


        val loadedTask = getValue(tasksDao.getAllTask())[0]

        assertThat<TaskModel>(loadedTask, notNullValue())
        assertThat(loadedTask.title, `is`("must be deleted"))
        assertThat(loadedTask.description, `is`("description"))

        db.tasksDao.delete(loadedTask)

        val loadedList = getValue(tasksDao.getAllTask())

        assertThat(loadedList.isEmpty() , `is`(true))
    }


    @Test
    @Throws(Exception::class)
    fun getAllTask() {
        fillDataBase()
        val dbListLiveData = db.tasksDao.getAllTask()
        val dbList = getValue(dbListLiveData)
        dbList.forEach {
            println(it.title)
        }


        // Assert.assertEquals(taskList, dbList)

    }


    private fun fillDataBase() {
        taskList.forEach {
            db.tasksDao.insert(it)
        }
    }*/

}
