package zohrevand.mahdi.taskmanager.dataAccess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
abstract class TaskManagerDatabase : RoomDatabase() {
    abstract val tasksDao: TasksDao

    companion object {
        @Volatile
        private var INSTANCE: TaskManagerDatabase? = null

        fun getInstance(context: Context): TaskManagerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskManagerDatabase::class.java,
                        "task_manager_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}