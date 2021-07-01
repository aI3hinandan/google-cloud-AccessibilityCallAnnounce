package com.gearlabs.accessibilitycallannounce

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

fun getJsonConfig(text: String,languageCode: String, voiceName: String, pitch: Double): JSONObject{
    val outer: JSONObject = JSONObject()
    val audioConfig: JSONObject = JSONObject()
    val input: JSONObject = JSONObject()
    val voice: JSONObject = JSONObject()
    val list = ArrayList<String>()
    list.add("handset-class-device")

    audioConfig.put("audioEncoding","LINEAR16")
    audioConfig.put("effectsProfileId", JSONArray(list))
    audioConfig.put("pitch", pitch)
    audioConfig.put("speakingRate", 1)

    input.put("text", text)

    voice.put("languageCode", languageCode)
    voice.put("name", voiceName)

    outer.put("audioConfig", audioConfig)
    outer.put("input", input)
    outer.put("voice", voice)

    return outer
}


fun setConfigTTS(context: Context, languageCode: String, voiceName: String, pitch: Double, name:String) {
    val jsonConfig: JSONObject = getJsonConfig(name,languageCode, voiceName, pitch)
    val jsonFile = jsonConfig.toString().toByteArray()
    saveFile(context,jsonFile, "Json Files", name)

}
