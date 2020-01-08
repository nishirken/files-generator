package main

import main.File
import main.Folder
import main.SettingsFile
import platform.posix.*
import kotlinx.cinterop.*

fun getHomeDir(): String? {
    return getenv("HOME")?.toKString()
}

fun main (args: Array<String>) {
    val homeDir = getHomeDir()
//    val parser = ArgParser("file-generator-parser")
//
//    val alias by parser.option(ArgType.String, shortName = "alias", description = "Set an alias")
//    val value by parser.option(ArgType.String, shortName = "value", description = "Set an alias")
//    println(alias)

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
