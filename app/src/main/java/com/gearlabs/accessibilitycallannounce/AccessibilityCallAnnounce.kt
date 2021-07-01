package com.gearlabs.accessibilitycallannounce

import android.app.Application
import android.content.Context


class AccessibilityCallAnnounce : Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        var instance: AccessibilityCallAnnounce? = null
            private set


        val context: Context?
            get() = instance

    }
}