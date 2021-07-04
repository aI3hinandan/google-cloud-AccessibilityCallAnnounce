package com.gearlabs.accessibilitycallannounce


import android.content.ContentValues
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import java.io.File


class MainActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_WRITE_CONTACTS: Int = 1
    val namesList: MutableList<String> = ArrayList()
    lateinit var model: MainViewModel
    val pContext = this
    var filteredList: MutableList<MutableList<String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        print(this.getFilesDir().toString())
        val output = makeFile("testing","final",this)
        /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + this.packageName)
                )
                startActivityForResult(intent, 200)
            }
        }*/

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickGet(view: View) {

        val list = onClickGetName(this)
        //  val unfilteredList : MutableList<MutableList<String>> = getCallLogs(this)
        filteredList = list
        val numberList: MutableList<String> = mutableListOf()
        for (i in list) {
            numberList.add(i[0])
        }
        Toast.makeText(this, "Contacts Retrieved", Toast.LENGTH_SHORT).show()
    }

    fun onClickSynthesize(v:View){
        for(contact in filteredList){
            val generic = getFileObj("Generic Audio","yaaas.wav",this)
            val custom = getFileObj("Synthesised Files", contact[2] + ".wav",this)
            makeFile("Final Files",contact[2] + ".wav",this)
            val output = getFileObj("Final Files",contact[2] + ".wav",this)
            val concat = Concat()
            concat.mergeWav(generic,custom,output)
        }
        Toast.makeText(this, "synt-Finished", Toast.LENGTH_SHORT).show()

    }


    fun onClickGetFromCloud(view: View) {
        var count = 0
        val newList:MutableList<MutableList<String>> = ArrayList()

        for(contact in filteredList){
            if (!(getFileObj("Synthesised Files","${contact[2]}.wav",this).exists())){
                count++
                newList.add(contact)
            }

        }

        Toast.makeText(this, count.toString(), Toast.LENGTH_SHORT).show()
        model.getTunesLog(newList)


        /*val part2 =
            getFileObj("name_audio_files/+919896215977", "namecall-en-IN-Wavenet-A-1.0.wav", this)
        val part1 = getFileObj("Generic Audio", "yaaas.wav", this)
        makeFile("name_audio_files/+919896215977", "final-en-IN-Wavenet-A-1.0.wav", this)
        val output =
            getFileObj("name_audio_files/+919896215977", "final-en-IN-Wavenet-A-1.0.wav", this)*/


    }




    fun onClickSet(v: View){
        var count = 0
        for(contact in filteredList){
            val number = contact[2]
            val k: File = getFileObj("Final Files","$number.wav",this)
            val bs:ByteArray = getFile("Final Files","$number.wav",this)
            val values = ContentValues()

            values.put(MediaStore.MediaColumns.DATA, k.absolutePath)
            values.put(MediaStore.MediaColumns.TITLE, number)
            values.put(MediaStore.MediaColumns.SIZE, 215454)
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/wav")
            values.put(MediaStore.Audio.Media.ARTIST, "GearLabs")
            values.put(MediaStore.Audio.Media.DURATION, 230)
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true)
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
            values.put(MediaStore.Audio.Media.IS_ALARM, false)
            values.put(MediaStore.Audio.Media.IS_MUSIC, false)


            val uri = MediaStore.Audio.Media.getContentUriForPath(k.absolutePath)
            contentResolver.delete(
                uri,
                MediaStore.MediaColumns.TITLE + "=\"" + number + "\"", null
            )

            contentResolver.delete(uri,
                MediaStore.MediaColumns.DATA + "=\""
                        + k.absolutePath + "\"", null
            )
            val newUri = contentResolver.insert(uri, values)

            ////////////////////////////////////////////////////////////
            val contentValues = ContentValues()
            contentValues.put(ContactsContract.Contacts.CUSTOM_RINGTONE, newUri.toString())
            val where = ContactsContract.Contacts.LOOKUP_KEY + "=?"
            val whereArgs = arrayOf(contact[2])
            val new:Int = contentResolver.update(ContactsContract.Contacts.CONTENT_URI, contentValues, where, whereArgs)
            print(new.toString())
            count += new
        }
        Toast.makeText(this, count.toString(), Toast.LENGTH_SHORT).show()


    }

 /*   fun onClickTest2(v: View) {
        var file1: File
        var file2: File
        val callback = object : VolleyCallback {
            override fun onSuccessResponse(result: ByteArray) {
                file1 = saveFile(this@MainActivity, result, "testing", "file1")
            }
        }
        val httpHelper = HttpHelper(callback_ = callback, context_ = this)
        httpHelper.makeRequestCallback("testing1", "en-IN", "en-IN-Wavenet-A", 1.0)

        val callback2 = object : VolleyCallback {
            override fun onSuccessResponse(result: ByteArray) {
                file2 = saveFile(this@MainActivity, result, "testing", "file1")
            }
        }
        val httpHelper2 = HttpHelper(callback_ = callback2, context_ = this)
        httpHelper2.makeRequestCallback("testing2", "en-IN", "en-IN-Wavenet-A", 1.0)
    }*/

}










