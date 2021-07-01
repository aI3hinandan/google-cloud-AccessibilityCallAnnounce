package com.gearlabs.accessibilitycallannounce

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FirestoreDatabase {
    private val db = Firebase.firestore
    val email: String = FirebaseAuth.getInstance().currentUser?.email.toString()
    var docRef = db.collection("TTSCalls").document(email)
    lateinit var listener: ListenerRegistration

    fun initialCalls() {
        val docRef = db.collection("TTSCalls").document(email)
        val user = hashMapOf("callsMade" to 0)
        docRef.set(user).addOnSuccessListener { Log.d(TAG, "sucess in adding to database of calls") }
            .addOnFailureListener{Log.d(TAG, "Failure in adding to database of calls")}
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun incrementCallsMade() {
        docRef = db.collection("TTSCalls").document(email)
        var callsMade: Long
        val fr = object : FirestoreResult {
            override fun onSuccessResponse(result: Long) {
                callsMade = result + 1
                val hm = hashMapOf("callsMade" to callsMade)
                docRef.set(hm)
                listener.remove()
            }
        }
        getCallsMade(fr)
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun getCallsMade(fr: FirestoreResult){
         docRef = db.collection("TTSCalls").document(email)
             listener = docRef.addSnapshotListener { snapshot, e ->
                if(e != null)     {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                    "Local"
                else
                    "Server"
                if (snapshot != null && snapshot.exists()) {
                    val callsMade: Long = snapshot["callsMade"] as Long
                    fr.onSuccessResponse(callsMade)

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
    }

}



