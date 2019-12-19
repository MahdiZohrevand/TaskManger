package zohrevand.mahdi.taskmanager.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*


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

    @Query("SELECT * from task_table ORDER BY create_date_milli DESC")
    fun getAllTask(): LiveData<List<TaskModel>>

    @Query("DELETE FROM task_table")
    fun clear()

}