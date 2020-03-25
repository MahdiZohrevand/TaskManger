package zohrevand.mahdi.taskmanager.dataAccess.preferences

import android.content.Context
import zohrevand.mahdi.taskmanager.R


const val USER_BIRTH_DATE = "USER_BIRTH_DATE"

class UserAgePreferenceImp(val context: Context) : UserAgePreference {

    private val sharedPref = context.getSharedPreferences(
        context.getString(R.string.age_preference),
        Context.MODE_PRIVATE
    )

    /**
     * save user birth date in millisecond in user age preference
     * @param dateInMilliseconds user birth date in millisecond
     */
    public override fun setUserBirthdateInMillisconds(dateInMilliseconds: Long) {
        with(sharedPref.edit()) {
            putLong(USER_BIRTH_DATE, dateInMilliseconds)
            commit()
        }
    }

    /**
     * get user birth date in millisecond
     * @return user birth date in millisecond if did not saved return -1
     */
    public override fun getUserBirthdateInMillisconds(): Long {
        return with(sharedPref) {
            getLong(USER_BIRTH_DATE, -1)
        }
    }

}