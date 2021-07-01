package com.gearlabs.accessibilitycallannounce

import android.app.IntentService
import android.content.Intent

class RingtoneService: IntentService(RingtoneService::class.simpleName) {
    override fun onHandleIntent(intent: Intent?) {

        //CallTrigger().onReceive(RingtoneCustomizer().applicationContext, intent)

    }

}