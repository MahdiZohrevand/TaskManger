package zohrevand.mahdi.taskmanager.dataAccess

import android.graphics.Color
import zohrevand.mahdi.customviewtest.model.Task
import java.util.*

public fun convertDbTaskListToDayViewTaskList(list: List<TaskModel>): MutableList<Task> {
    val taskList = mutableListOf<Task>()
    val calendar = Calendar.getInstance()
    list.forEach {

        taskList.add(
            Task(
                it.TaskId,
                it.title,
                it.description,
                calendar.apply { timeInMillis = it.startDate.time }.get(Calendar.HOUR_OF_DAY)
                    .toFloat(),
                calendar.get(Calendar.MINUTE).toFloat(),
                calendar.apply { timeInMillis = it.finishDate!!.time }.get(Calendar.HOUR_OF_DAY)
                    .toFloat(),
                calendar.get(Calendar.MINUTE).toFloat(),
                Color.parseColor("#e44285F4"),
                startDate = it.startDate,
                endDate = it.finishDate

            )
        )
    }

    return taskList

}