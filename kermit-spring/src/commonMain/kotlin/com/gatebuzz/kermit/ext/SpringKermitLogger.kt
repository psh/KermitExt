package com.gatebuzz.kermit.ext

import co.touchlab.kermit.BaseLogger

class SpringKermitLogger(
    config: SpringLoggerConfig,
    open val tag: String = ""
) : BaseLogger(config.copy()) {
}