package com.gearlabs.accessibilitycallannounce

import android.content.Context
import android.util.Base64
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


private const val API_KEY = ""
class HttpHelper(context_: Context) {
    val context: Context
    val queue: RequestQueue
    init {
        context = context_
        queue = Volley.newRequestQueue(context)
    }

    fun makeRequestCallback(callback:VolleyCallback, data: MutableList<String> ,language: String, voice: String, pitch: Double ) {
        val url = "https://texttospeech.googleapis.com/v1/text:synthesize?key=" + API_KEY
        val jsonRequest = getJsonConfig(data[0],language, voice, pitch)
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST ,url, jsonRequest,
            {
                    response -> callback.onSuccessResponse(Base64.decode(response.getString("audioContent"), Base64.DEFAULT),data[2])
            },
            {
            })
        queue.add(jsonObjectRequest)
        queue.start()
    }


    /*fun makeRequestCallback(callback:VolleyCallback) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://texttospeech.googleapis.com/v1/text:synthesize?key=" + API_KEY
        val jsonStream = File(context.getExternalFilesDir(null).toString() + "/Json Files/json-english.json").readBytes()
        val jsonString = String(jsonStream, Charset.forName("UTF-8"))
        val jsonRequest = JSONObject(jsonString)
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST ,url, jsonRequest,
            {
                    response -> callback.onSuccessResponse(Base64.decode(response.getString("audioContent"), Base64.DEFAULT))
            },
            {
            })
        queue.add(jsonObjectRequest)
        queue.start()
    }

    fun makeRequestCallback(callback:VolleyCallback, path: String) {
        val url = "https://texttospeech.googleapis.com/v1/text:synthesize?key=" + API_KEY
        val jsonStream = File(path).readBytes()
        val jsonString = String(jsonStream, Charset.forName("UTF-8"))
        val jsonRequest = JSONObject(jsonString)
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST ,url, jsonRequest,
            {
                    response -> callback.onSuccessResponse(Base64.decode(response.getString("audioContent"), Base64.DEFAULT))
            },
            {
            })
        queue.add(jsonObjectRequest)
        queue.start()
    }



    fun makeRequest(callback:VolleyCallback, name: MutableList<String> ,language: String, voice: String, pitch: Double ) {
        val queue = Volley.newRequestQueue(context)
        val number = name[1]
        val url = "https://texttospeech.googleapis.com/v1/text:synthesize?key=" + API_KEY
        val jsonRequest = getJsonConfig(name[0],language, voice, pitch)
        val jsonObjectRequest = JsonObjectRequest( Request.Method.POST ,url, jsonRequest,
            {
                    response ->
                val bs = Base64.decode(response.getString("audioContent"), Base64.DEFAULT)
                saveFile(context,bs,"name_audio_files/$number","namecall-$voice-$pitch.wav")
                val part2 = getFileObj("name_audio_files/$number","namecall-$voice-$pitch.wav", context)
                val part1 = getFileObj("Generic Audio","yaaas.wav",context)
                val output = getFileObj("name_audio_files/$number","final-$voice-$pitch.wav", context)
                mergeAudio(part1,part2,output)
            },
            {
            })
        queue.add(jsonObjectRequest)
        queue.start()
    }*/

}
