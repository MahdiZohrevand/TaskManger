package zohrevand.mahdi.customviewtest.model

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize


@Parcelize
//because of conflict with getter method and the interface method's name add "_" to each property name
class Task(
    val _id: Long = -1,
    val _tittle: String,
    val _description: String = "",
    val _startTimeHour: Float,
    val _startTimeMinute: Float,
    val _endTimeHour: Float,
    val _endTimeMinute: Float,
    @ColorInt var _rectColor: Int? = null,
    @ColorInt var _textColor: Int? = null
) : CalendarTask, Parcelable {


    override fun getID(): Long {
        return this._id
    }

    override fun getTitle(): String {
        return _tittle
    }

    override fun getDescription(): String {
        return _description
    }

    override fun getStartTimeHour(): Float {
        return _startTimeHour
    }

    override fun getStartTimeMinute(): Float {
        return _startTimeMinute
    }

    override fun getEndTimeHour(): Float {
        return _endTimeHour
    }

    override fun getEndTimeMinute(): Float {
        return _endTimeMinute
    }

    @ColorInt
    override fun getRectColor(): Int? {
        return _rectColor
    }

    @ColorInt
    override fun getTextColor(): Int? {
        return _textColor
    }


}