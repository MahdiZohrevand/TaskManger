package zohrevand.mahdi.taskmanager

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import zohrevand.mahdi.taskmanager.view.MainActivity

@RunWith(AndroidJUnit4::class)
class TempTestLaunchActivity {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test fun testEvent() {
        val scenario = activityScenarioRule.scenario
    }
}