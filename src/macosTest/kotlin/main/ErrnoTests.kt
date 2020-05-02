package main

import kotlin.test.Test
import main.errnoMap
import main.Errno
import kotlin.test.assertTrue

class ErrnoTests {
    @Test
    fun first() {
        assertTrue { errnoMap[1] == Errno.EPERM }
    }

    @Test
    fun mid() {
        assertTrue { errnoMap[66] == Errno.EREMOTE }
    }

    @Test
    fun last() {
        assertTrue { errnoMap[Errno.values().size] == Errno.EMEDIUMTYPE }
    }
}
