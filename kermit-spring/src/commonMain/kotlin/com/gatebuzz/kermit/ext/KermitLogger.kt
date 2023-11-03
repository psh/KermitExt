package com.gatebuzz.kermit.ext

@Suppress("unused")
inline fun <reified T> T.kermitLogger(tag: String? = null) = lazy {
    if (tag.isNullOrBlank()) {
        KermitLoggerFactory.withTag(T::class.java)
    } else {
        KermitLoggerFactory.withTag(tag)
    }
}
