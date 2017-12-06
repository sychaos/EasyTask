package cloudist.cc.easyscheduler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cloudist.cc.library.EasyTask
import cloudist.cc.library.callback.DefaultCallback
import cloudist.cc.library.process.Processes
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
                    .callbackOn(Processes.mainThread())
                    .runOn(Processes.background())
                    .callback(object : DefaultCallback<Objects>() {
                        override fun error(t: Throwable?) {
                            text.text = "error"
                        }
                    })
                    .run()
        }, 3000)

    }
}
