package zohrevand.mahdi.taskmanager.dataAccess

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TasksDao {

    @Insert
    suspend fun insert(taskModel: TaskModel)

    @Update
    suspend fun update(taskModel: TaskModel)

    @Delete
    suspend fun delete(taskModel: TaskModel)

    @Query("SELECT * from task_table WHERE taskId = :key")
    suspend fun get(key: Long): TaskModel?

    @Query("SELECT * from task_table ORDER BY create_date_milli DESC")
    fun getAllTask(): LiveData<List<TaskModel>>

}