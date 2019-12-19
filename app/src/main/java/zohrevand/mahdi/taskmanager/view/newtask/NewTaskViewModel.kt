package zohrevand.mahdi.taskmanager.view.newtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import zohrevand.mahdi.taskmanager.dataAccess.TaskManagerDatabase
import zohrevand.mahdi.taskmanager.dataAccess.TaskModel

class NewTaskViewModel(val db: TaskManagerDatabase) : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    val taskMutableLiveData = db.tasksDao.getAllTask()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        taskMutableLiveData.observeForever {
            val s = it
        }

        uiScope.launch {

        }
    }

    fun onSubmitClick() {
        description.value = "button was clicked"
        uiScope.launch {
            inserNewTaskDb()
        }
    }


    suspend fun inserNewTaskDb() {
        withContext(Dispatchers.IO) {
            val taskDao = db.tasksDao
            taskDao.insert(TaskModel(title = "FirstTask", description = "FirstDescription"))

        }
    }


    suspend fun getAllTask() {
        withContext(Dispatchers.IO) {
            val tasksDao = db.tasksDao
            tasksDao.getAllTask().observeForever {
                val x = it

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}