package com.gearlabs.accessibilitycallannounce


interface VolleyCallback {
    fun onSuccessResponse(result: ByteArray, number: String)
}

interface JsonCheck {
    fun onSuccessResponse(result: ByteArray)
}

interface FirestoreResult {
    fun onSuccessResponse(result: Long)
}

interface VolleyCallback2 {
    fun onSuccessResponse(result: ByteArray, number: String)
}
