package bare.bones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.tag("Lifecycle").i("onCreate")

        findViewById<TextView>(R.id.hello_world).setOnClickListener {
            Timber.e(Exception("Dont Click Me"))
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("Lifecycle").i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("Lifecycle").i("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("Lifecycle").i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("Lifecycle").i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("Lifecycle").i("onDestroy")
    }
}