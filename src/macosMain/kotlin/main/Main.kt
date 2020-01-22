package main

import main.File
import main.Folder
import main.SettingsFile
import main.Arguments
import main.env
import platform.posix.*
import ui.*

fun classContent(name: String): String {
    return "export class $name {}\n"
}

fun interfaceContent(name: String): String {
    return "export interface $name {}\n"
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

    file.write(classContent(name))
    interfaceFile.write(interfaceContent("I$name"))
    mockFile.write(classContent(name))
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
    makeWindow()
    val homeDir = env.homeDir()
    createSettingsFolder(homeDir).createIfNotExists()
    val settingsFile = createSettingsFile(homeDir)
    settingsFile.createIfNotExists()

    val parsedArgs = Arguments(args)
    val setAliasArgument = parsedArgs.getSetAliasArgument()

    if (setAliasArgument !== null) {
        settingsFile.setAlias(setAliasArgument.first, setAliasArgument.second)
        return
    }

    val pathArg = parsedArgs.getPathArgument() ?: env.cwd() ?: "/"
    val nameArg = parsedArgs.getNameArgument() ?: return
    val postfix = parsedArgs.getPostfixArgument() ?: settingsFile.getAliasValue(parsedArgs.getAlias())

    makeFiles(nameArg, pathArg, postfix)
}
