package bare.bones

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.gatebuzz.kermit.ext.Slf4jLogWriter
import com.gatebuzz.kermit.ext.withTag

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    Logger.setLogWriters(Slf4jLogWriter())
    Logger.setMinSeverity(Severity.Verbose)

    val logger = Logger.withTag(App::class.java)

    val greeting: String = App().greeting

    logger.i("Hello World")

    logger.i("App says: $greeting")

    logger.i("App says: $greeting and again $greeting")

    logger.i {
        "Let's count %d, %d, %d".format(1, 2, 3)
    }

    val otherLogger = Logger.withTag("bare.bones.App")
    otherLogger.w("Hello World - 1")

    otherLogger.e("Log an exception", Exception("with message"))

    otherLogger.e(Exception("with message")) {
        "Log another exception"
    }
}
