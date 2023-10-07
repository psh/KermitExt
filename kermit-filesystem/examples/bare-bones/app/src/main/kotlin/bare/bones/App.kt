package bare.bones

import co.touchlab.kermit.*
import com.gatebuzz.kermit.ext.FilesystemLogWriter
import com.gatebuzz.kermit.ext.Kermit

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val rootLogger = Kermit {
        minSeverity(Severity.Verbose)

        + platformLogWriter()

        + FilesystemLogWriter {
            logPath("./logs/log.txt")
            rollLogAtSize(1200)    // Bytes (Optional) - skip if you dont want logs to roll
        }
    }

    val loggerWithTag = rootLogger.withTag(App::class.java.name)

    val greeting: String = App().greeting

    rootLogger.i("Hello World")

    loggerWithTag.i("App says: $greeting")

    loggerWithTag.i("App says: $greeting and again $greeting")

    loggerWithTag.i {
        "Let's count %d, %d, %d".format(1, 2, 3)
    }

    val otherLogger = rootLogger.withTag("bare.bones.App")
    otherLogger.w("Hello World - 1")

    otherLogger.e("Log an exception", Exception("with message"))

    otherLogger.e(Exception("with message")) {
        "Log another exception"
    }
}
