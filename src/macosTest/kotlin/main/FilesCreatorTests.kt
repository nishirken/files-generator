package main

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import main.env

class FilesCreatorTests {
    private val testFolderName: String = "testFolder"
    private var testFolder: Folder = Folder(testFolderName)
    private val fileName: String = "testFile"
    private val postfix: String = "State"
    private var filesCreator: FilesCreator = FilesCreator(fileName, "testFolder", postfix)

    @BeforeTest
    fun beforeEach() {
        testFolder.createIfNotExists()
        val cwd = env.cwd()
        if (cwd != null) filesCreator = FilesCreator(fileName, joinPaths(cwd, "testFolder"), postfix)
    }

    @AfterTest
    fun afterEach() {
        testFolder.remove()
    }

//    @Test
//    fun `create files without test`() {
//        filesCreator.createOnlyFilesWithoutTest()
//        assertBaseFileCreation()
//        assertInterfaceFileCreation()
//    }

    private fun assertBaseFileCreation() {
        assertTrue { File(joinPaths(testFolderName, fileName)).isExists() }
    }

    private fun assertInterfaceFileCreation() {
        assertTrue { File(joinPaths(testFolderName, "I$fileName")).isExists() }
    }
}
