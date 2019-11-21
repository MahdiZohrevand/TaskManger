package zohrevand.mahdi.taskmanager.view.age

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import zohrevand.mahdi.taskmanager.dataAccess.preferences.UserAgePreferenceImp
import zohrevand.mahdi.taskmanager.utils.PersianCalendar
import zohrevand.mahdi.taskmanager.utils.ageInMillisecond
import zohrevand.mahdi.taskmanager.utils.calculateAge
import java.util.*

//TODO preferenceImp must be replace with interface not implementation

class AgeViewModel(val preferenceImp: UserAgePreferenceImp) : ViewModel() {

    private var year: Int = -1
    private var month: Int = -1
    private var day: Int = -1


    val yearLiveData = MutableLiveData<Int>()
    val monthLiveData = MutableLiveData<Int>()
    val dayLiveData = MutableLiveData<Int>()

    var spinnerYearPosition: Int = 1
    var spinnerMonthPosition: Int = 1
    var spinnerDayPosition: Int = 1

    val userAge = MutableLiveData<String>()


    init {
        val birthDateInMilli = preferenceImp.getUserBirthdateInMillisconds()
        //user have been used age calculator and show last used calculated value
        if (birthDateInMilli > 1) {
            calculateAge(birthDateInMilli) { year, month, day, age ->
                spinnerYearPosition = year
                spinnerMonthPosition = month
                spinnerDayPosition = day
                userAge.value = age
            }
        }


        yearLiveData.observeForever {
            year = it
        }

        monthLiveData.observeForever {
            month = it
        }

        dayLiveData.observeForever {
            day = it
        }

    }


    fun onCalculateClick() {
        preferenceImp.setUserBirthdateInMillisconds(ageInMillisecond(year, month, day))
        userAge.value = calculateAge(year, month, day)
    }

}
