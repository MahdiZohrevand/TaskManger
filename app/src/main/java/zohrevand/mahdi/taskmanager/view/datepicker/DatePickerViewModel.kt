package zohrevand.mahdi.taskmanager.view.datepicker

import androidx.lifecycle.ViewModel
import saman.zamani.persiandate.PersianDate
import java.util.*

class DatePickerViewModel : ViewModel() {


    //===================================time

    private var pCalendar = PersianDate()

    var yearPosition = pCalendar.shYear
    var monthPosition = pCalendar.shMonth - 1
    var dayPosition = pCalendar.shDay - 1


    public fun getDate(): Date? {
        pCalendar.shYear = yearPosition
        pCalendar.shMonth = monthPosition + 1
        pCalendar.shDay = dayPosition + 1
        return pCalendar.toDate()
    }

}