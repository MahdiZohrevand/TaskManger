package zohrevand.mahdi.taskmanager.view.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is New Task Fragment"
    }
    val text: LiveData<String> = _text
}