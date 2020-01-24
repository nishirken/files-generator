package main

import main.File
import main.Folder
import main.SettingsFile
import main.Arguments
import main.env
import platform.posix.*
import ui.*

fun classContent(name: String, interfaceName: String): String {
    return """
        import { $interfaceName } from './$interfaceName';

        export class $name implements $interfaceName {}
    """
}

fun mockContent(name: String, interfaceName: String, path: String): String {
    return """
        import { $interfaceName } from ${joinPaths(path, interfaceName)};

        export class $name implements $interfaceName {}
    """
}

fun interfaceContent(name: String): String {
    return "export interface $name {}\n"
}

fun testContent(name: String): String {
    return """
        import { $name } from './$name'; 
    
        return "describe('$name', () => {});" 
    """
}

fun makeFiles(name: String, path: String, postfix: String = "", withTest: Boolean = true) {
    val extension = ".ts"
    val className = name + postfix
    val classFileName = className + extension
    val testFileName = "$className.test$extension"
    val interfaceName = "I$className"
    val interfaceFileName = interfaceName + extension
    val file = File(joinPaths(path, classFileName))
    val interfaceFile = File(joinPaths(path, interfaceFileName))
    val mocksFolder = Folder(joinPaths(path, "__mocks__"))
    val mockFile = File(joinPaths(joinPaths(path, "__mocks__"), classFileName))

    file.createIfNotExists()
    interfaceFile.createIfNotExists()
    mocksFolder.createIfNotExists()
    mockFile.createIfNotExists()

    file.write(classContent(className, interfaceName))
    interfaceFile.write(interfaceContent(interfaceName))
    mockFile.write(mockContent(className, interfaceName, path))

    if (withTest) {
        val testFile = File(joinPaths(path, testFileName))
        testFile.createIfNotExists()
        testFile.write(testContent(className))
    }
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

    makeFiles(nameArg, pathArg, postfix, withTest)
}
