package main

import main.File
import main.Folder
import main.SettingsFile
import main.parseArgs
import platform.posix.*
import kotlinx.cinterop.*
import interop.*

fun getHomeDir(): String? {
    return getenv("HOME")?.toKString()
}

fun setup() {
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

fun main (args: Array<String>) {
    setup()

    val parsedArgs = parseArgs(args)
    for (arg in parsedArgs) {
        println(arg)
    }

    val cwd = getCwd()?.toKString()
}
