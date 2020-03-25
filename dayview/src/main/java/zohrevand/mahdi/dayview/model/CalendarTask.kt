package zohrevand.mahdi.customviewtest.model

interface CalendarTask {
    fun getID(): Long
    fun getTitle(): String
    fun getDescription(): String?
    fun getStartTimeHour(): Float
    fun getStartTimeMinute(): Float
    fun getEndTimeHour(): Float
    fun getEndTimeMinute(): Float
    fun getRectColor(): Int?
    fun getTextColor(): Int?
}