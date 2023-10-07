package com.gatebuzz.kermit.ext

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

interface LogRoller {
    fun rollLogs(okioPath: Path, fileSystem: FileSystem)
}

class FileSizeLogRoller(
    private val logPath: String,
    private val maxFileSize: Long
) : LogRoller {
    override fun rollLogs(okioPath: Path, fileSystem: FileSystem) {
        val metadata = fileSystem.metadataOrNull(okioPath)
        metadata?.let {
            it.size?.let { size ->
                if (size >= maxFileSize) {
                    val filename = okioPath.segments.last()
                    val count = fileSystem.list(okioPath.parent!!).count { p ->
                        p.name.contains(filename)
                    }
                    val to = "$logPath.$count".toPath()
                    fileSystem.atomicMove(okioPath, to)
                }
            }
        }
    }
}