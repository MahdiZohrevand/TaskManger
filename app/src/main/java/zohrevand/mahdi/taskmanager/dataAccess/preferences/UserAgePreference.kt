package zohrevand.mahdi.taskmanager.dataAccess.preferences

import java.time.Month

interface UserAgePreference {

    fun setUserBirthdateInMillisconds(dateInMilliseconds : Long)
    fun getUserBirthdateInMillisconds() : Long
}