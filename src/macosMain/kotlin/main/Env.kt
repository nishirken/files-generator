package main

import kotlinx.cinterop.*
import interop.*
import platform.posix.getenv

object env {
    fun homeDir(): String? {
        return getenv("HOME")?.toKString()
    }

    fun cwd(): String? {
        return getCwd()?.toKString()
    }
}
