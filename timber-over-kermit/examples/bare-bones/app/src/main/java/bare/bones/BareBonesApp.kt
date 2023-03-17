package bare.bones

import android.app.Application
import android.util.Log
import co.touchlab.kermit.*
import com.gatebuzz.kermit.ext.TreeFrog
import timber.log.Timber

class BareBonesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Minimal Kermit configuration
        val logger = BaseLogger(
            StaticConfig(
                logWriterList = listOf(platformLogWriter())
            )
        )

        // For clarity, only use the "timber over kermit" log tree
        Timber.uprootAll()
        Timber.plant(TreeFrog(logger))
    }
}
