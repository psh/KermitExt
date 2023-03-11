package com.gatebuzz.kermit.ext

import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.StaticConfig
import org.slf4j.ILoggerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.helpers.NOPMDCAdapter

class KermitServiceProvider : org.slf4j.spi.SLF4JServiceProvider {
    private val markerFactory = BasicMarkerFactory()
    private val mdcAdapter = NOPMDCAdapter()
    override fun getLoggerFactory() = ILoggerFactory { Slf4jKermitLogger(config) }

    override fun getMarkerFactory() = markerFactory

    override fun getMDCAdapter() = mdcAdapter

    override fun getRequestedApiVersion() = "2.0.99"

    override fun initialize() = Unit

    companion object {
        var config: LoggerConfig = StaticConfig()
    }
}