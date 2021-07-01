package com.gearlabs.accessibilitycallannounce

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(): ViewModel() {
    val context: Context

    init {
        context = AccessibilityCallAnnounce.context!!
    }

     fun getTunesLog(names: MutableList<MutableList<String>>){
        viewModelScope.launch {
            ringtoneWork(names)
        }
    }

  suspend fun ringtoneWork(names: MutableList<MutableList<String>>) =
      withContext(Dispatchers.IO) {
          val appConfig = AppConfig()
          val mode = appConfig.mode
          val lang = appConfig.lang
          val voice = appConfig.voice
          val language = appConfig.language
          val pitch = appConfig.pitch
          val genericCallback:VolleyCallback = object: VolleyCallback {
              override fun onSuccessResponse(result: ByteArray, number:String) {
                  val returnedVoice = result
                  saveFile(context,returnedVoice,"Generic Audio","yaaas.wav")
              }
          }
          val singleCallback: VolleyCallback = object: VolleyCallback{
              override fun onSuccessResponse(result: ByteArray, number: String) {
                  val returnedVoice = result
                  saveFile(context,returnedVoice,"Synthesised Files", number + ".wav")
              }

          }
          val httpHelper = HttpHelper(context)
          httpHelper.makeRequestCallback(genericCallback,
              mutableListOf("You have a call from","",""),language,voice, pitch)
          var count = 0

          for(contact in names){
                httpHelper.makeRequestCallback(singleCallback,
                  contact,language,voice,pitch
                  )
          }


          /*val genericCallback: VolleyCallback = object: VolleyCallback {
              override fun onSuccessResponse(result: ByteArray) {
                  val returnedVoice = result
                  saveFile(context,returnedVoice,"Generic Audio","yaaas2.wav")
                  for(name in names){
                      httpHelper.makeRequest(,name,language,voice,pitch)
                  }
              }
          }
          val specificCallback: VolleyCallback

          httpHelper = HttpHelper(callback_ = genericCallback, context_ = context)
          httpHelper.makeRequestCallback("You have a call from",language,voice, pitch)*/

      }
}