package sample

import main.hello
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testHello() {
        assertTrue("Kotlin/Native" in hello())
    }
}
