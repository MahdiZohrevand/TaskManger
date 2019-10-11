package zohrevand.mahdi.taskmanager.dataAccess

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class TaskModel(

    @PrimaryKey(autoGenerate = true)
    var TaskId: Long = 0L,

    @ColumnInfo(name = "task_title")
    val title: String,

    @ColumnInfo(name = "task_description")
    val description: String,

    @ColumnInfo(name = "create_date_milli")
    val createTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_date_milli")
    var endTimeMilli: Long = -1,

    @ColumnInfo(name = "deadline_date_milli")
    var deadLineTimeMilli: Long = -1,

    @ColumnInfo(name = "spanned_time_milli")
    var spannedTimeMilli: Long = -1,

    @ColumnInfo(name = "is_done")
    var isDone: Boolean = false

)