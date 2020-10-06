package zohrevand.mahdi.taskmanager.utils

import androidx.databinding.BindingAdapter
import zohrevand.mahdi.customviewtest.model.Task
import zohrevand.mahdi.dayview.view.DayView


@BindingAdapter("setTasks")
fun setDayViewTasks(dayView: DayView , tasks: List<Task>){
    dayView.addCalendarTask(tasks)
}