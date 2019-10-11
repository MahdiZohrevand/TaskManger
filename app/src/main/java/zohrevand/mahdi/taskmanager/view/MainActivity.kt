package zohrevand.mahdi.taskmanager.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import zohrevand.mahdi.taskmanager.R

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private val job = Job()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.main_text)



       /* GlobalScope.launch {
            val result = get()
            withContext(Dispatchers.Main){
                textView.text = result
            }
        }*/

    }


    private suspend fun changeText(){
        val result = get()
        textView.text = result
    }

    private suspend fun get(): String {
        delay(3000)
        return "we done"
    }
}
