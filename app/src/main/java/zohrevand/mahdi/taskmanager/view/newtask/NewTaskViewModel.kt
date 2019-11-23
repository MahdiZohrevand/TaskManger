package zohrevand.mahdi.taskmanager.view.newtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.TaskModel

class NewTaskViewModel(val db: TaskManagerDatabase) : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()


    fun onSubmitClick() {
        description.value = "button was clicked"
    }
}