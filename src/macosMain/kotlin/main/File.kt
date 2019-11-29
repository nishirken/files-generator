package main

import main.FileObject
import platform.posix.*
import kotlinx.cinterop.*

open class File(private val filename: String) : FileObject {
    override fun isExists(): Boolean {
        return access(filename, F_OK) != -1
    }

    open fun create(): CPointer<FILE>? {
        return fopen(filename, "w+")
    }
}
