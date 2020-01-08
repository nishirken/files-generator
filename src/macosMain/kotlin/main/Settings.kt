package main

import main.File
import main.Folder
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import platform.posix.*
import kotlinx.cinterop.*

val settingsFolder = ".file-generator"
val defaultFolder = "/temp"

fun makeSettingsFolderName(basePath: String?): String {
    return (basePath ?: defaultFolder) + "/" + settingsFolder
}

val settingsFileName = "settings.json"

fun makeSettingsFileName(basePath: String?): String {
    return makeSettingsFolderName(basePath) + "/" + settingsFileName
}

@Serializable
data class SettingsContent(val aliases: Map<String, String>) {}

class SettingsFile(private val filename: String) : File(filename) {
    override fun isExists(): Boolean {
        return super.isExists()
    }

    override fun create(): Unit {
        super.create()
        write(makeJsonContent(HashMap()))
    }

    fun setAlias(key: String, value: String): Unit {
        val content = read()
        val settings = Json(JsonConfiguration.Stable).parse<SettingsContent>(SettingsContent.serializer(), content)
        write(makeJsonContent(settings.aliases.plus(Pair(key, value))))
    }

    private fun makeJsonContent(aliases: Map<String, String>): String {
        return Json(JsonConfiguration.Stable).stringify(SettingsContent.serializer(), SettingsContent(aliases))
    }
}
