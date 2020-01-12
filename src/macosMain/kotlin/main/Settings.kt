package main

import main.File
import main.Folder
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import platform.posix.*
import kotlinx.cinterop.*

const val settingsFolder = ".file-generator"
const val defaultFolder = "/temp"
const val settingsFileName = "settings.json"

@Serializable
data class SettingsContent(val aliases: Map<String, String>) {}

fun makeSettingsFolderName(basePath: String?): String {
    return (basePath ?: defaultFolder) + "/" + settingsFolder
}

fun createSettingsFile(basePath: String?): SettingsFile {
    val settingsFolderName = makeSettingsFolderName(basePath)
    return SettingsFile("$settingsFolderName/$settingsFileName")
}

fun createSettingsFolder(basePath: String?): Folder {
    return Folder(makeSettingsFolderName(basePath))
}

class SettingsFile(private val filename: String) : File(filename) {
    private val defaultPostfix: String = "State"

    override fun isExists(): Boolean {
        return super.isExists()
    }

    override fun create(): Unit {
        super.create()
        write(makeJsonContent(HashMap()))
    }

    fun setAlias(key: String, value: String): Unit {
        val content = read()
        val settings = Json(JsonConfiguration.Stable).parse(SettingsContent.serializer(), content)
        write(makeJsonContent(settings.aliases.plus(Pair(key, value))))
    }

    fun parse(): Map<String, String> {
        return Json(JsonConfiguration.Stable).parse(SettingsContent.serializer(), read()).aliases
    }

    fun getAliasValue(alias: String?): String {
        if (alias == null) return "State"
        return parse()[alias] ?: "State"
    }

    private fun makeJsonContent(aliases: Map<String, String>): String {
        return Json(JsonConfiguration.Stable).stringify(SettingsContent.serializer(), SettingsContent(aliases))
    }
}
