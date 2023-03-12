package bare.bones

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.gatebuzz.kermit.ext.KermitServiceProvider
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.BasicMarkerFactory

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    // Try filtering the minimum debug level
    KermitServiceProvider.minSeverity = Severity.Info

    // Custom Kermit log writer
    KermitServiceProvider.setWriters(object : LogWriter() {
        override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
            println("\n###> Severity = $severity")
            println("###> Tag/Message = $tag / $message")
            throwable?.let {
                println("###> Throwable - ${it::class.java.name}")
                it.printStackTrace()
            }
        }
    })

    val logger: Logger = LoggerFactory.getLogger(App::class.java)

    val greeting: String = App().greeting

    logger.info("Hello World")

    logger.info("App says: %s", greeting)

    logger.info("App says: %s and again %s", greeting, greeting)

    logger.info("Let's count %d, %d, %d", 1, 2, 3)

    val factory = BasicMarkerFactory()
    val marker1 = factory.getMarker("marker1")
    val marker2 = factory.getMarker("marker2")
    marker1.add(marker2)

    logger.debug(marker1, "Hello World - 1")

    logger.debug(marker2, "Hello World - 2")

    logger.error("Log an exception", Exception("with message"))
}
