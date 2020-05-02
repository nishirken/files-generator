package main

import main.SettingsFile
import kotlin.math.exp
import kotlin.test.*

class SettingsTests {
    private val testFileName: String = "settings.json"
    private var settingsFile: SettingsFile = SettingsFile(testFileName)

    @BeforeTest
    fun beforeTest() {
        settingsFile = SettingsFile(testFileName)
    }

    @AfterTest
    fun afterTest() {
        settingsFile.remove()
    }

    @Test
    fun `create settings file and write empty content`() {
        settingsFile.create()
        assertTrue { settingsFile.isExists() }
        assertEquals("{\"aliases\":{}}", settingsFile.read())
    }

    @Test
    fun `set alias`() {
        settingsFile.create()
        settingsFile.setAlias("s", "State")
        assertEquals("{\"aliases\":{\"s\":\"State\"}}", settingsFile.read())
    }

    @Test
    fun `get aliases`() {
        settingsFile.create()
        settingsFile.setAlias("s", "State")
        assertEquals(hashMapOf(Pair("s", "State")), settingsFile.parse() )
    }

    @Test
    fun `get alias by value`() {
        settingsFile.create()
        settingsFile.setAlias("s", "State")
        assertEquals(settingsFile.getAliasValue("s"), "State")
    }
}
