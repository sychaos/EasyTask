package cloudist.cc.easyscheduler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cloudist.cc.library.EasyTask
import cloudist.cc.library.callback.DefaultCallback
import cloudist.cc.library.process.Processes

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.text)

        text.postDelayed({
            EasyTask.create<List<String>> {
                Thread.sleep(1000)
                listOf("s","sd","sds")
            }
                    .callbackOn(Processes.mainThread())
                    .runOn(Processes.background())
                    .callback(object : DefaultCallback<List<String>>() {
                        override fun error(t: Throwable?) {
                            text.text = "error"
                        }

                        override fun onFinish(t: List<String>?) {
                            text.text = t.toString()
                        }
                    })
                    .run()
        }, 3000)

    }
}
