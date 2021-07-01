package com.gearlabs.accessibilitycallannounce

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.CallLog
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import androidx.annotation.RequiresApi

/////GET A LIST OF ALL THE CONTACTS////////////////////////////////////////////////////////////////////////////////////
fun onClickGetName(context: Context): MutableList<MutableList<String>> {
    val contactList: MutableList<MutableList<String>> = ArrayList(3)
    //val namesList: MutableList<Any> = ArrayList()
   // val numbersList: MutableList<Any> = ArrayList()
    val cr: ContentResolver = context.contentResolver
    val cur = cr.query(
        ContactsContract.Contacts.CONTENT_URI,
        null, null, null, null
    )

    if (cur.count > 0) {
        while (cur.moveToNext()) {
            val name: String = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
            val contactId = cur.getString(cur.getColumnIndex(ContactsContract.Data._ID))
            val contactLookup = cur.getString(cur.getColumnIndex(ContactsContract.Data.LOOKUP_KEY))
            var phone: String = "+910000000000"

            val contact: MutableList<String> = ArrayList(2)
            contact.add(0, name)
            //namesList.add(name)
            val contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId.toString())
            val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(contactId) ,null)
            while (pCur.moveToNext()) {
                phone = PhoneNumberUtils.normalizeNumber(pCur.getString(
                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
            }
            contact.add(1,phone)
            contact.add(2,contactLookup)
            pCur.close();
            contactList.add(contact)

        }
        cur.close()
    }

    return contactList
}
////CODE FOR API USE OPTIMIZATION/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//IMPLEMENTATION REMAINING////////////////////////////////////////
@RequiresApi(Build.VERSION_CODES.O)
fun getCallLogs(context: Context): MutableList<MutableList<String>> {
    val contactList: MutableList<MutableList<String>> = ArrayList()
    val cr: ContentResolver = context.contentResolver
    val cursor = cr.query(CallLog.Calls.CONTENT_URI, arrayOf(CallLog.Calls.CACHED_NAME,CallLog.Calls.CACHED_FORMATTED_NUMBER), null, null)
    if (cursor.count > 0) {
        while (cursor.moveToNext()) {
            var name: String? = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME))
            var number: String? = PhoneNumberUtils.normalizeNumber(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_FORMATTED_NUMBER)))
            val contact: MutableList<String> = ArrayList()

            if (name != null && number != null) {
                contact.add(0,name)
                contact.add(0,number)
            }
            contactList.add(contact)
        }
    }
    return contactList
}

@RequiresApi(Build.VERSION_CODES.O)
fun getNumberLogs(context: Context): MutableList<String> {
    val list: MutableList<String> = mutableListOf()
    val cr: ContentResolver = context.contentResolver
    val cursor = cr.query(
        CallLog.Calls.CONTENT_URI,
        arrayOf(CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_FORMATTED_NUMBER),
        null,
        null
    )
    if (cursor.count > 0) {
        while (cursor.moveToNext()) {
            var name: String? = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME))
            var number: String? =
                PhoneNumberUtils.normalizeNumber(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)))

            if (name != null && number != null) {
                list.add(number)
            }

        }
    }
    return list
}



fun onClickGetName2(context: Context): MutableList<String> {
    val contactList: MutableList<String> = ArrayList()
    //val namesList: MutableList<Any> = ArrayList()
    // val numbersList: MutableList<Any> = ArrayList()
    val cr: ContentResolver = context.contentResolver
    val cur = cr.query(
        ContactsContract.Contacts.CONTENT_URI,
        null, null, null, null
    )

    if (cur.count > 0) {
        while (cur.moveToNext()) {
            val name: String = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
            val contactId = cur.getString(cur.getColumnIndex(ContactsContract.Data._ID))
            var phone: String
            val contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId.toString())
            val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(contactId) ,null)
            while (pCur.moveToNext()) {
                phone = PhoneNumberUtils.normalizeNumber(pCur.getString(
                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                contactList.add(phone)
            }
            pCur.close();


        }
        cur.close()
    }

    return contactList
}


