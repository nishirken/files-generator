package main

import main.File
import main.Folder
import main.SettingsFile
import main.Arguments
import platform.posix.*
import kotlinx.cinterop.*
import interop.*

fun getHomeDir(): String? {
    return getenv("HOME")?.toKString()
}

fun makeFiles(name: String, path: String, postfix: String = "") {
    val extension = ".ts"
    val finalName = name + postfix + extension
    val interfaceName = "I$finalName"
    val file = File(joinPaths(path, finalName))
    val interfaceFile = File(joinPaths(path, interfaceName))
    val mocksFolder = Folder(joinPaths(path, "__mocks__"))
    val mockFile = File(joinPaths(joinPaths(path, "__mocks__"), finalName))

    file.createIfNotExists()
    interfaceFile.createIfNotExists()
    mocksFolder.createIfNotExists()
    mockFile.createIfNotExists()
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
    val homeDir = getHomeDir()
    createSettingsFolder(homeDir).createIfNotExists()
    val settingsFile = createSettingsFile(homeDir)
    settingsFile.createIfNotExists()

    val parsedArgs = Arguments(args)
    val setAliasArgument = parsedArgs.getSetAliasArgument()

    if (setAliasArgument !== null) {
        settingsFile.setAlias(setAliasArgument.first, setAliasArgument.second)
        return
    }

    val pathArg = parsedArgs.getPathArgument() ?: getCwd()?.toKString() ?: "/"
    val nameArg = parsedArgs.getNameArgument() ?: return
    val postfix = parsedArgs.getPostfixArgument() ?: settingsFile.getAliasValue(parsedArgs.getAlias())

    makeFiles(nameArg, pathArg, postfix)
}
