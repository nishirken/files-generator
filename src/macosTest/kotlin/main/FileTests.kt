package main

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class FileTests {
    private val fileName: String = "testFile.txt"
    private var file: File = File(fileName)

    @BeforeTest
    fun beforeEach() {
        file = File(fileName)
    }

    @AfterTest
    fun afterEach() {
        if (file.isExists()) {
            file.remove()
        }
    }

    @Test
    fun `create and exist`() {
        file.create()
        assertTrue { file.isExists() }
    }

    @Test
    fun `override file if already exist`() {
        file.create()
        file.write("oldContent")
        file.create()
        assertTrue { file.read() == "" }
    }

    @Test
    fun `doesn't create file if already exist`() {
        file.create()
        file.write("content")
        file.createIfNotExists()
        assertTrue { file.read() == "content" }
    }

    @Test
    fun `remove file`() {
        file.create()
        file.remove()
        assertTrue { !file.isExists() }
    }

    @Test
    fun `write and read`() {
        val content = "test content"
        file.create()
        file.write(content)
        assertTrue { file.read() == content }
    }
}
