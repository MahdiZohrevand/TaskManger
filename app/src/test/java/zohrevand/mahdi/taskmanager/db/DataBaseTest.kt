package zohrevand.mahdi.taskmanager.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.TaskModel
import java.lang.Exception

import zohrevand.mahdi.taskmanager.util.LiveDataUtilTest.getValue


@RunWith(AndroidJUnit4::class)
class DataBaseTest : KoinTest {

    val db: TaskManagerDatabase by inject()


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    @Throws(Exception::class)
    fun insertAndGetTask() {

        val taskModel =
            TaskModel(title = "test", description = "description", spannedTimeMilli = 7000L)

        db.tasksDao.insert(taskModel)

        //data base has only one item
        val loadedTask = getValue(db.tasksDao.getAllTask())[0]



        println(loadedTask.title)


        //loaded data contains excepted values
        MatcherAssert.assertThat<TaskModel>(loadedTask, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loadedTask.title, CoreMatchers.`is`("test"))
        MatcherAssert.assertThat(loadedTask.description, CoreMatchers.`is`("description"))
        MatcherAssert.assertThat(loadedTask.spannedTimeMilli, CoreMatchers.`is`(7000L))
    }


    @Test
    fun insertTaskAndDelete() {
        val taskModel = TaskModel(
            title = "must be deleted",
            description = "description",
            spannedTimeMilli = 7000L
        )
        db.tasksDao.insert(taskModel)


        val loadedTask = getValue(db.tasksDao.getAllTask())[0]

        MatcherAssert.assertThat<TaskModel>(loadedTask, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loadedTask.title, CoreMatchers.`is`("must be deleted"))
        MatcherAssert.assertThat(loadedTask.description, CoreMatchers.`is`("description"))

        db.tasksDao.delete(loadedTask)

        val loadedList = getValue(db.tasksDao.getAllTask())

        MatcherAssert.assertThat(loadedList.isEmpty(), CoreMatchers.`is`(true))
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
        val taskList = listOf(
            TaskModel(title = "1", description = "1"),
            TaskModel(title = "2", description = "2"),
            TaskModel(title = "3", description = "3")
        )
        taskList.forEach {
            db.tasksDao.insert(it)
        }
    }

    @After
    fun afterTest() {
        db.close()
        stopKoin()
    }

}