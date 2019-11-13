package zohrevand.mahdi.taskmanager.view.age

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import zohrevand.mahdi.taskmanager.dataAccess.preferences.UserAgePreferenceImp
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import java.util.*

class AgeViewModel(val preferenceImp: UserAgePreferenceImp) : ViewModel() {

    var year: Int = -1
    var month: Int = -1
    var day: Int = -1

    val userAge = MutableLiveData<String>()



    init {
        val birthDateInMilli = preferenceImp.getUserBirthdateInMillisconds()
        if (birthDateInMilli > 1) {
            val birthDate = Calendar.getInstance()
            birthDate.timeInMillis = birthDateInMilli
            val rightNow = Calendar.getInstance()
            val diff = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)
            userAge.value = "${diff.years} سال ${diff.months} ماه ${diff.days} روز "

        }
    }


    fun calculateAge() {
        val birthDate = PersianCalendar.getGregorianCalendar(year, month, day)
        preferenceImp.setUserBirthdateInMillisconds(birthDate.timeInMillis)
        val rightNow = Calendar.getInstance()
        val diff = PersianCalendar.getPeriodOfTwoTime(birthDate, rightNow)

        userAge.value = "${diff.years} سال ${diff.months} ماه ${diff.days} روز "
    }


    fun setSpinnerDate(spinner: (year: Int, month: Int, day: Int) -> Unit) {
        spinner(1, 2, 3)
    }

}
