package main

import main.File
import main.Folder
import main.SettingsFile
import platform.posix.*
import kotlinx.cinterop.*

//val appendCmd = "append"

fun getHomeDir(): String? {
    return getenv("HOME")?.toKString()
}

fun main (args: Array<String>) {
    val homeDir = getHomeDir()
    val settingsFolder = Folder(makeSettingsFolderName(homeDir))
    if (!settingsFolder.isExists()) {
        settingsFolder.create()
    }
    val settingsFile = SettingsFile(makeSettingsFileName(homeDir))
    if (!settingsFile.isExists()) {
        settingsFile.create()
    }
}
