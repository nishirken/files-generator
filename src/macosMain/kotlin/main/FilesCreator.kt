package main

import main.Folder
import main.File
import platform.posix.index

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

class FilesCreator(baseName: String, private val path: String, private val postfix: String = "") {
    private val extension: String = ".ts"
    private val baseName: String = baseName + postfix
    private val interfaceName: String = "I$baseName"

    private val baseFileName: String = baseName + extension
    private val interfaceFileName: String = interfaceName + extension
    private val testFileName: String = "$baseName.test$extension"
    private val indexFileName: String = "index$extension"

    fun createFull() {
        val folderName = joinPaths(path, baseName)
        val folder = Folder(folderName)
        folder.create()
        createFile(testFileName, folderName)
        createFile(indexFileName, folderName)
        createFile(baseFileName, folderName)
        createFile(interfaceFileName, folderName)
        createMocksFolder(folderName)
        createFile(baseFileName, getMocksFolderPath(folderName))
    }

    fun createOnlyFiles() {
        createOnlyFilesWithoutTest()
        createFile(testFileName, path)
    }

    fun createOnlyFilesWithoutTest() {
        createFile(baseFileName, path)
        createFile(interfaceFileName, path)
        createMocksFolder(path)
        createFile(baseFileName, getMocksFolderPath(path))
    }

    private fun createMocksFolder(basePath: String) {
        Folder(getMocksFolderPath(basePath)).create()
    }

    private fun createFile(fileName: String, basePath: String) {
        val file = File(joinPaths(fileName, basePath))
        file.create()
    }

    private fun getMocksFolderPath(basePath: String): String {
        return joinPaths("__mocks__", basePath)
    }
}
