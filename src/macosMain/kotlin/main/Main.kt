package main

import main.File
import main.Folder
import main.SettingsFile
import main.parseArgs
import platform.posix.*
import kotlinx.cinterop.*

fun getHomeDir(): String? {
    return getenv("HOME")?.toKString()
}

fun main (args: Array<String>) {
    val homeDir = getHomeDir()
    val parsedArgs = parseArgs(args)
    for (arg in parsedArgs) {
        println(arg)
    }

    val settingsFolder = Folder(makeSettingsFolderName(homeDir))
    if (!settingsFolder.isExists()) {
        settingsFolder.create()
    }
    val settingsFile = SettingsFile(makeSettingsFileName(homeDir))
    if (!settingsFile.isExists()) {
        settingsFile.create()
    }
    settingsFile.setAlias("s", "State")
    settingsFile.setAlias("s", "State")
    settingsFile.setAlias("r", "State")
}
