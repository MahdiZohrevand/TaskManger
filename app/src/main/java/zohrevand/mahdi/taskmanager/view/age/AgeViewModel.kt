package zohrevand.mahdi.taskmanager.view.age

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import java.util.*

class AgeViewModel : ViewModel() {

    var year: Int = -1
    var month: Int = -1
    var day: Int = -1

    val userAge = MutableLiveData<String>()


    fun calculateAge() {
        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
        val rightNow = Calendar.getInstance()
        val diff = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)

        userAge.value = "${diff.years} سال ${diff.months} ماه ${diff.days} روز "
    }

}
