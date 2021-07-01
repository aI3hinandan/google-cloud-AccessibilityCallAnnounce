package com.gearlabs.accessibilitycallannounce

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.io.File

class JsonConfigActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    val context: Context = this
    var selectedLanguage = "en_IN"
    var selectedVoice= "en-US-Wavenet-A"
    var selectedPitch: Double = 1.00


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json_config)
        val languageSpinner: Spinner = findViewById(R.id.spinner_language)
        val voiceSpinner: Spinner = findViewById(R.id.spinner_voice)
        val pitchBar: FloatSeekBar = findViewById(R.id.seekbar_pitch)
        ArrayAdapter.createFromResource(
            this,
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            languageSpinner.adapter = adapter
            languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    selectedLanguage =  languageSpinner.selectedItem.toString()
                    val voiceSpinner: Spinner = findViewById(R.id.spinner_voice)
                    val item = parent?.getItemAtPosition(position)
                    if(item == "en-US" ){
                        ArrayAdapter.createFromResource(
                            context,
                            R.array.voice_english_us_array,
                            android.R.layout.simple_spinner_item
                        ).also {
                                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            voiceSpinner.adapter = adapter
                            voiceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedVoice = voiceSpinner.selectedItem.toString()
                                }

                            }
                        }
                    }

                    if(item == "en-IN" ){
                        ArrayAdapter.createFromResource(
                            context,
                            R.array.voice_english_india_array,
                            android.R.layout.simple_spinner_item
                        ).also {
                                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            voiceSpinner.adapter = adapter
                            voiceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedVoice = voiceSpinner.selectedItem.toString()
                                }

                            }
                        }
                    }

                    if(item == "hi-IN" ){
                        ArrayAdapter.createFromResource(
                            context,
                            R.array.voice_hindi_array,
                            android.R.layout.simple_spinner_item
                        ).also {
                                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            voiceSpinner.adapter = adapter
                            voiceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedVoice = voiceSpinner.selectedItem.toString()
                                }

                            }

                        }
                    }

                }
            }
        }
        pitchBar.setOnSeekBarChangeListener(this)
        }

    override fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        val valuePitch: TextView = findViewById(R.id.textview_value_pitch)
        val floatSeekBar = seekBar as FloatSeekBar
        valuePitch.setText(floatSeekBar.value.toDouble().toString())
        selectedPitch = floatSeekBar.value.toDouble()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        seekBar.also {  }
    }


    fun onCheck(view: View) {
        val callback : VolleyCallback = object : VolleyCallback {
            override fun onSuccessResponse(result: ByteArray,number:String) {
                val temp: File = File.createTempFile("testAudio", "mp3", this@JsonConfigActivity.cacheDir ).apply{
                    deleteOnExit()
                }
                temp.writeBytes(result)
                val mp = MediaPlayer()
                mp.setDataSource(temp.absolutePath)
                mp.prepare()
                mp.start()
            }

        }
        val httpHelper = HttpHelper(context_ = this)
        httpHelper.makeRequestCallback(callback,
            mutableListOf("You have a call from This contact",""),selectedLanguage,selectedVoice,selectedPitch)
    }
}




