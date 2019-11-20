package zohrevand.mahdi.taskmanager.utils

import android.R.layout
import android.view.View
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

fun Spinner.setNumberAdapter(min: Int, max: Int, revers: Boolean) {
    val numberList = mutableListOf<Int>()

    if (revers) {
        for (element in max downTo min) {
            numberList.add(element)
        }
    } else {
        for (element in min..max) {
            numberList.add(element)
        }
    }

    val adapter =
        ArrayAdapter<Int>(context!!, layout.simple_spinner_dropdown_item, numberList)
    this.adapter = adapter
}

fun Spinner.onItemSelected(onItemSelected: (spinnerNumber: Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            onItemSelected(-1)
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            onItemSelected(
                if (parent?.selectedItem is Int) parent.selectedItem as @ParameterName(
                    name = "spinnerNumber"
                ) Int else -1
            )
        }

    }
}

@BindingAdapter("setPosition")
fun setPosition(spinner: Spinner, position: Int) {
    spinner.setSelection(position)
}

@BindingAdapter("getPosition")
fun getItem(spinner: Spinner, observer: MutableLiveData<Int>){
    spinner.onItemSelected {
        observer.value = it
    }
}