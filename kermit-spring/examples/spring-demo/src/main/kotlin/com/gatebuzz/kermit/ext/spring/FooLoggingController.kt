package com.gatebuzz.kermit.ext.spring

import com.gatebuzz.kermit.ext.kermitLogger
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FooLoggingController {
    private val logger by kermitLogger()

    @RequestMapping("/foo")
    fun index(): String {
        logger.v("A VERBOSE / TRACE Message")
        logger.d("A DEBUG Message")
        logger.i("An INFO Message")
        logger.w("A WARN Message")
        logger.e("An ERROR Message")

        return "Howdy! Check out the Logs to see the output.  Edit the \"kermit.properties\" to change log levels and formatting."
    }
}