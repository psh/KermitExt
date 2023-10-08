package bare.bones

import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import com.gatebuzz.kermit.ext.Kermit
import com.gatebuzz.kermit.ext.withBrightColor
import com.gatebuzz.kermit.ext.withColor

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val logger = Kermit {
        minSeverity(Severity.Verbose)

        + platformLogWriter(withBrightColor())
    }

    logger.v("Hello World")

    logger.i("Hello World")

    logger.d("Hello World")

    logger.w("Hello World")

    logger.e("Hello World")

    logger.a("Hello World")
}
