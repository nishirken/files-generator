package main

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class FolderTests {
    private val folderName: String = "testFolder"
    private var folder: Folder = Folder(folderName)

    @BeforeTest
    fun beforeEach() {
        folder = Folder(folderName)
    }

    @AfterTest
    fun afterEach() {
        folder.remove()
    }

    @Test
    fun `create and exists`() {
        folder.create()
        assertTrue { folder.isExists() }
    }

    @Test
    fun `create if not exists`() {
        folder.create()
        folder.createIfNotExists()
        assertTrue { folder.isExists() }
    }
}
