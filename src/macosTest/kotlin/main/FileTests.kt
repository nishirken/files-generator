package main

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class FileTests {
    private val fileName: String = "testFile"
    private var file: File = File(fileName)

    @BeforeTest
    fun beforeEach() {
        file = File(fileName)
    }

    @AfterTest
    fun afterEach() {
        file.remove()
    }

    @Test
    fun `create and exists`() {
        file.create()
        assertTrue { file.isExists() }
    }

    @Test
    fun `create if not exists`() {
        file.create()
        file.createIfNotExists()
        assertTrue { file.isExists() }
    }

    @Test
    fun `write and read`() {
        val content = "test content"
        file.create()
        file.write(content)
        assertTrue { file.read() == content }
    }
}
