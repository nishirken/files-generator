package main

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import main.File

class FolderTests {
    private val folderName: String = "testFolder"
    private var folder: Folder = Folder(folderName)

    @BeforeTest
    fun beforeEach() {
        folder = Folder(folderName)
    }

    @AfterTest
    fun afterEach() {
        if (folder.isExists()) {
            folder.remove()
        }
    }

    @Test
    fun `create and exists`() {
        folder.create()
        assertTrue { folder.isExists() }
    }

//    @Test
//    fun `override folder if already exists`() {
//        folder.create()
//        val file = File("$folderName/testFile.txt")
//        file.create()
//        folder.create()
//        assertTrue { !file.isExists() }
//    }

//    @Test
//    fun `doesn't create folder if already exists`() {
//        folder.create()
//        val file = File("$folderName/testFile.txt")
//        file.create()
//        folder.createIfNotExists()
//        assertTrue { file.isExists() }
//    }
}
