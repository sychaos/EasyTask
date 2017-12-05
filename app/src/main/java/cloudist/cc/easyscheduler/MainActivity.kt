package cloudist.cc.easyscheduler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import cloudist.cc.library.Callback
import cloudist.cc.library.EasyTask
import cloudist.cc.library.process.Schedules
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.text)

        text.postDelayed({
            EasyTask.create<Objects> {
                throw RuntimeException("故意在子线程抛出异常")
            }
                    .callbackOn(Schedules.mainThread())
                    .runOn(Schedules.background())
                    .run(object : Callback<Objects> {
                        override fun error(t: Throwable?) {
                            text.text = "error"
                        }

                        override fun onStart() {
                            text.text = "onStart"
                        }

                        override fun onFinish() {
                            text.text = "onFinish"
                        }
                    })
        }, 3000)

    }
}
