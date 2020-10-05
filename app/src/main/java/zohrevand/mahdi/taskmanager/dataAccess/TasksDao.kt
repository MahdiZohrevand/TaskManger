package zohrevand.mahdi.taskmanager.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


@Dao
interface TasksDao {

    @Insert
    fun insert(taskModel: TaskModel)

    @Update
    fun update(taskModel: TaskModel)

    @Delete
    fun delete(taskModel: TaskModel)

    @Query("SELECT * from task_table WHERE taskId = :key")
    fun get(key: Long): TaskModel?

    @Query("SELECT * from task_table WHERE start_date BETWEEN :start AND :end")
    fun getTaskForDay(start: Date, end: Date): LiveData<List<TaskModel>>

    @Query("SELECT * from task_table ORDER BY start_date DESC")
    fun getAllTask(): LiveData<List<TaskModel>>

    @Query("DELETE FROM task_table")
    fun clear()

}