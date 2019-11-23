package zohrevand.mahdi.taskmanager.utils

import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import java.util.*

@BindingAdapter("onTextChanged")
fun getEditTextText(editText: EditText, observable: MutableLiveData<String>) {
    editText.doAfterTextChanged {
        observable.value = it.toString()
    }
}


@BindingAdapter("android:text")
fun setText(editText: EditText, text: MutableLiveData<String>) {
    val odlValue = editText.text.toString()
    val newValue = text.value

    if (odlValue != newValue) {
        editText.setText(newValue, TextView.BufferType.EDITABLE)
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(editText: EditText): String =
    editText.text.toString()

@BindingAdapter("android:textAttrChanged")
fun setListener(editText: EditText, attrChang: InverseBindingListener) {
    editText.doAfterTextChanged {
        attrChang.onChange()
    }
}