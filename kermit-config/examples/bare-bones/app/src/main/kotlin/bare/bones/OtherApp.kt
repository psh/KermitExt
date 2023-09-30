package bare.bones

import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import com.gatebuzz.kermit.ext.Kermit

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val logger = Kermit.builder()
        .minSeverity(Severity.Warn)
        .addLogWriter(platformLogWriter())
        .build()

    val loggerWithTag = logger.withTag(App::class.java.name)

    val greeting: String = App().greeting

    logger.i("Hello World")

    loggerWithTag.i("App says: $greeting")

    loggerWithTag.i("App says: $greeting and again $greeting")

    loggerWithTag.i {
        "Let's count %d, %d, %d".format(1, 2, 3)
    }

    val otherLogger = logger.withTag("bare.bones.App")
    otherLogger.w("Hello World - 1")

    otherLogger.e("Log an exception", Exception("with message"))

    otherLogger.e(Exception("with message")) {
        "Log another exception"
    }
}
