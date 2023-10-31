package com.gatebuzz.kermit.ext

inline fun <reified T> T.kermitLogger(tag: String? = null) = lazy {
    if (tag.isNullOrBlank()) {
        KermitLoggerFactory.withTag(T::class.java)
    } else {
        KermitLoggerFactory.withTag(tag)
    }
}
