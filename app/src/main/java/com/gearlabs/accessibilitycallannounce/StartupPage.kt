package com.gearlabs.accessibilitycallannounce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StartupPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup_page)
        if(!configExists(this)){
            val ac = AppConfig()
            ac.makeDefaultConfig()
        }

    }


    fun onClick(v: View) {
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)


    }
}