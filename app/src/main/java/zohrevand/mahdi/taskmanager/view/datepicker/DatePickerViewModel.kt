package zohrevand.mahdi.taskmanager.view.datepicker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import saman.zamani.persiandate.PersianDate
import java.util.*

class DatePickerViewModel : ViewModel() {

    val monthLive = MutableLiveData<Int>()
    val yearLive = MutableLiveData<Int>()

    var maxMonth: ((max: Int, dayPosition: Int) -> Unit)? = null


    //===================================time

    private var pCalendar = PersianDate()

    var yearPosition = pCalendar.shYear
    var monthPosition = pCalendar.shMonth - 1
    var dayPosition = pCalendar.shDay - 1

    init {
        monthLive.observeForever {
            val max = pCalendar.getMonthLength(yearPosition, it)
            monthPosition = it - 1
            maxMonth?.invoke(max, dayPosition)
        }

        yearLive.observeForever {
            val max = pCalendar.getMonthLength(it, monthPosition + 1)
            yearPosition = it
            maxMonth?.invoke(max, dayPosition)
        }
    }

    public fun getDate(): Date? {
        pCalendar.shYear = yearPosition
        pCalendar.shMonth = monthPosition + 1
        pCalendar.shDay = dayPosition + 1
        return pCalendar.toDate()
    }

}