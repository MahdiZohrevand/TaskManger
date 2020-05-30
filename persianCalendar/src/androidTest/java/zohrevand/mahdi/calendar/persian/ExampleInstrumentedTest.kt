package zohrevand.mahdi.calendar.persian

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("zohrevand.mahdi.calendar.persian.test", appContext.packageName)
    }


    @Test
    fun age_test() {
        val instrumentationContext = InstrumentationRegistry.getInstrumentation().context

        AndroidThreeTen.init(instrumentationContext)

        val birthdate = LocalDate.of(1970, 1, 20)
        val now = LocalDate.now()
        val period = Period.between(birthdate , now)
        Log.d("age" , "test")
        System.out.println("${period.years} ${period.months} ${period.days}")
    }
}