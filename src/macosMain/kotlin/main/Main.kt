package main

import main.File
import main.Folder
import main.SettingsFile
import main.Arguments
import main.env
import main.FilesCreator
import platform.posix.*
import ui.*

fun classContent(name: String, interfaceName: String): String {
    return (
"""import { $interfaceName } from './$interfaceName';

export class $name implements $interfaceName {}
"""
    )
}

fun mockContent(name: String, interfaceName: String, path: String): String {
    return (
"""import { $interfaceName } from '${joinPaths(path, interfaceName)}';

export class $name implements $interfaceName {}
"""
    )
}

fun interfaceContent(name: String): String {
    return "export interface $name {}\n"
}

fun testContent(name: String): String {
    return (
"""import { $name } from './$name'; 

describe('$name', () => {}); 
"""
    )
}

fun main (args: Array<String>) {
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
    val withTest = !parsedArgs.getWithoutTestArgument()
    val withDirectory = parsedArgs.getWithDirectory()

    val creator = FilesCreator(nameArg, pathArg, postfix)

    if (withDirectory) {
        creator.createFull()
    } else if (withTest) {
        creator.createOnlyFiles()
    } else {
        creator.createOnlyFilesWithoutTest()
    }
}
