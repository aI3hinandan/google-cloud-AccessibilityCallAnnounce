package com.gearlabs.accessibilitycallannounce

import android.content.Context
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset

fun configExists(context: Context): Boolean {
    return isFile("config", "config.json", context)
}

class AppConfig {
    val context: Context? = AccessibilityCallAnnounce.context
    var lang = 0
    var mode = "standard"
    var language = "en-IN"
    var voice = "en-IN-Wavenet-A"
    var pitch = 1.00

    init {
        if (File(context?.getExternalFilesDir(null).toString() + "/config/config.json").isFile) {
            val jsonStream = File(context?.getExternalFilesDir(null).toString() + "/config/config.json").readBytes()
            val jsonString = String(jsonStream, Charset.forName("UTF-8"))
            val jsonRequest = JSONObject(jsonString)
            lang = jsonRequest.getInt("lang")
            mode = jsonRequest.getString("mode")
            language = jsonRequest.getString("language")
            voice = jsonRequest.getString("voice")
            pitch = jsonRequest.getDouble("pitch")

        }
    }


    fun makeDefaultConfig() {
        val config = JSONObject()
        config.put("mode", "standard")
        config.put("lang", 0)
        config.put("language", "en-IN")
        config.put("voice", "en-IN-Wavenet-A")
        config.put("pitch", 1.00)

        val configString = config.toString().toByteArray()
        if (context != null) {
            saveFile(context, configString, "config", "config.json")
        }
    }

    fun makeConfig(
        mode: String,
        lang: Int,
        language: String,
        voice: String,
        pitch: Double
    ) {
        val config = JSONObject()
        config.put("mode", mode)
        config.put("lang", lang)
        config.put("language", language)
        config.put("voice", voice)
        config.put("pitch", pitch)

        val configString = config.toString().toByteArray()
        if (context != null) {
            saveFile(context, configString, "config", "config.json")
        }
    }
}


