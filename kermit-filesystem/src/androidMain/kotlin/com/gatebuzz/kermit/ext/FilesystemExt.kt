package com.gatebuzz.kermit.ext

import okio.FileSystem

actual fun fileSystem() : FileSystem = FileSystem.SYSTEM