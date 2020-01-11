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

fun makeFiles(name: String, path: String, postfix: String = "") {
    val extension = ".ts"
    val finalName = name + postfix + extension
    val interfaceName = "I$finalName$extension"
    createFile(joinPaths(path, finalName).cstr)
    createFile(joinPaths(path, interfaceName).cstr)
    createDirectory(joinPaths(path, "__mocks__").cstr)
    createFile(joinPaths(joinPaths(path, "__mocks__"), finalName).cstr)
}

fun joinPaths(xs: String, ys: String): String {
    var fst = xs
    var snd = ys
    if (xs.endsWith("/")) {
        fst = xs.substring(0, xs.length - 2)
    }
    if (ys.startsWith("/")) {
        snd = xs.substring(1, ys.length - 1)
    }
    return "$fst/$snd"
}

fun main (args: Array<String>) {
    setup()

    val parsedArgs = parseArgs(args)
    val pathArg = findValue("path", parsedArgs)

    var nameArg = findValue("name", parsedArgs)

    if (nameArg == null) {
        nameArg = findValue("n", parsedArgs)
    }

    if (nameArg == null) {
        return
    }

    val postfixArg = findValue("p", parsedArgs)
    var postfix = ""

    if (postfixArg == null) {
        postfix = "State"
    } else {
        postfix = postfixArg[0]
    }

    if (pathArg != null) {
        val value = pathArg[0]
        makeFiles(nameArg[0], value, postfix)
    } else {
        var cwd = getCwd()?.toKString()

        if (cwd == null) {
            cwd = "/"
        }
        makeFiles(nameArg[0], cwd, postfix)
    }
}
