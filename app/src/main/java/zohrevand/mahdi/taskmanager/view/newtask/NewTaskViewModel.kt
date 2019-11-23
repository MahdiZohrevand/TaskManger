package zohrevand.mahdi.taskmanager.view.newtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTaskViewModel : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()


    fun onSubmitClick() {
        description.value = "button was clicked"
    }
}