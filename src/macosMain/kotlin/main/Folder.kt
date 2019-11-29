package main

import main.FileObject
import platform.posix.*
import kotlinx.cinterop.*

class Folder(private val folderName: String) : FileObject {
    override fun isExists(): Boolean {
        return opendir(folderName) != null
    }

    fun create(): Unit {
        mkdir(folderName, S_IRWXU.or(S_IXGRP).or(S_IXOTH).toUShort())
    }
}
