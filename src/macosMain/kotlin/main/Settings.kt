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

    override fun create(): CPointer<FILE>? {
        val pointer = super.create()

        if (pointer != null) {
            val json = Json(JsonConfiguration.Stable)
            fputs(json.stringify(SettingsContent.serializer(), SettingsContent(HashMap())), pointer)
        }

        return pointer
    }
}
