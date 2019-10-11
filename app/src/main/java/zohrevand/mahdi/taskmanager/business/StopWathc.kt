package zohrevand.mahdi.taskmanager.business

import java.util.*

class StopWatch {

    var startTime: Long = -1


    fun start() {
        startTime = Calendar.getInstance().timeInMillis
    }

    fun stop() {
        startTime = -1
    }

    fun getSpannedTime(): Long? {
        if (startTime < 0) {
            return null
        }
        return Calendar.getInstance().timeInMillis - startTime
    }

    fun stopAndGetSpannedTime(): Long? {
        val spannedTime = getSpannedTime()
        stop()
        return spannedTime
    }


}