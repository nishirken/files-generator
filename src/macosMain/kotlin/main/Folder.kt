package main

import main.FileObject
import platform.posix.*
import interop.*
import kotlinx.cinterop.cstr
import main.errnoMap
import main.InteropException

class Folder(private val folderName: String) : FileObject {
    override fun isExists(): Boolean {
        return opendir(folderName) != null
    }

    override fun create() {
        if (isExists()) {
            remove()
        }

        val status = mkdir(folderName, S_IRWXU.or(S_IRWXG).or(S_IRWXO).toUShort())
        if (status != 0) {
            throw InteropException("Create folder $folderName")
        }
    }

    override fun remove() {
        if (!isExists()) {
            return
        }
        val status = removeDir(folderName.cstr)

        if (status != 0) {
            throw InteropException("Remove folder $folderName")
        }
    }

    override fun createIfNotExists() {
        if (!isExists()) {
            create()
        }
    }
}
