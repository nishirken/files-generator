package main

import main.FileObject
import platform.posix.*
import kotlinx.cinterop.*

open class File(private val filename: String) : FileObject {
    override fun isExists(): Boolean {
        return access(filename, F_OK) != -1
    }

    override fun create(): Unit {
        fopen(filename, "a")
    }

    open fun read(): String {
        val pointer = fopen(filename, "r")
        if (pointer == null) {
            return ""
        }
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

    open fun write(content: String): Unit {
        val p = fopen(filename, "w")
        fputs(content, p)
        fclose(p)
    }
}
