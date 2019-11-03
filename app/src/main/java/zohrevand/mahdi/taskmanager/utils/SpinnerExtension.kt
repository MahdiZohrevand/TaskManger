package zohrevand.mahdi.taskmanager.utils

import android.R.layout
import android.widget.ArrayAdapter
import android.widget.Spinner

fun Spinner.setNumberAdapter( min: Int, max: Int) {
    val numberList = mutableListOf<Int>()
    for (element in max downTo min) {
        numberList.add(element)
    }
    val adapter =
        ArrayAdapter<Int>(context!!, layout.simple_spinner_dropdown_item, numberList)
    this.adapter = adapter
}