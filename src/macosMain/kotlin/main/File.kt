package main

import main.FileObject
import platform.posix.*
import kotlinx.cinterop.*
import main.InteropException

open class File(private val filename: String) : FileObject {
    override fun isExists(): Boolean {
        return access(filename, F_OK) != -1
    }

    override fun create() {
        val pointer = fopen(filename, "w")
        pointer ?: throw InteropException("Create file $filename")
    }

    override fun createIfNotExists() {
        if (!isExists()) {
            create()
        }
    }

    override fun remove() {
        val status = remove(filename)
        if (status != 0) {
            throw InteropException("Remove file $filename")
        }
    }

    open fun read(): String {
        val pointer = fopen(filename, "r")
        pointer ?: throw InteropException("Read file $filename")
        var content = ""
        memScoped {
            val bufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(bufferLength)
            var line = fgets(buffer, bufferLength, pointer)?.toKString()

            while (line != null) {
                content += line
                line = fgets(buffer, bufferLength, pointer)?.toKString()
            }
        }
        return content
    }

    open fun write(content: String) {
        val pointer = fopen(filename, "w")
        pointer ?: throw InteropException("Write file $filename")
        fputs(content, pointer)
        fclose(pointer)
    }
}
