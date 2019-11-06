package zohrevand.mahdi.taskmanager.utils

import android.R.layout
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

fun Spinner.setNumberAdapter(min: Int, max: Int) {
    val numberList = mutableListOf<Int>()
    for (element in max downTo min) {
        numberList.add(element)
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