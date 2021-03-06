package main

import platform.posix.*
import kotlinx.cinterop.*

interface FileObject {
    fun isExists(): Boolean
    fun create(): Unit
    fun createIfNotExists(): Unit
    fun remove(): Unit
}
