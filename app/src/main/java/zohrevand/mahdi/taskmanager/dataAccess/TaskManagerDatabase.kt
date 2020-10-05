package zohrevand.mahdi.taskmanager.dataAccess

import android.content.Context
import androidx.room.*


@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
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
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}