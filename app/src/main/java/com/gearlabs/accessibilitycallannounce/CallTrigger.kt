package com.gearlabs.accessibilitycallannounce

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.provider.MediaStore
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import java.io.File


class CallTrigger:
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.extras.getString(TelephonyManager.EXTRA_STATE) == (TelephonyManager.EXTRA_STATE_RINGING)) {
                if(!intent.extras.containsKey(TelephonyManager.EXTRA_INCOMING_NUMBER)){
                        return
                    }
                val settings = AppConfig()
                val voice = settings.voice
                val pitch = settings.pitch
                var number: String =
                    PhoneNumberUtils.normalizeNumber(intent.extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER))
                val context: Context = AccessibilityCallAnnounce.context!!
               val file = File(
                    context.getExternalFilesDir(null).toString() + "/test.txt"
                )
                val bc = number.toByteArray()
                file.createNewFile()
                file.writeBytes(bc)
                val k: File = getFileObj("Synthesised Files","$number.wav",context)
                val bs:ByteArray = getFile("Synthesised Files","$number.wav",context)
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
                context.contentResolver.delete(
                    uri,
                    MediaStore.MediaColumns.TITLE + "=\"" + "new" + "\"", null
                )

                context.contentResolver.delete(uri,
                    MediaStore.MediaColumns.DATA + "=\""
                            + k.absolutePath + "\"", null
                )
                val newUri = context.contentResolver.insert(uri, values)

                RingtoneManager.setActualDefaultRingtoneUri(
                    context,
                    RingtoneManager.TYPE_RINGTONE,
                    newUri
                )

                saveFile(context,bs,"Synthesised Files","$number.wav")
            }
            }
        }
    }


