
package com.gearlabs.accessibilitycallannounce

import android.content.Context
import java.io.File

fun  saveFile( context: Context, bs: ByteArray, dir: String, fileName: String ): File {
    var directory = File(context.getExternalFilesDir(null).toString() + "/$dir/")
    var file = File(context.getExternalFilesDir(null).toString() + "/$dir/" + fileName )
    directory.mkdirs()
    file.createNewFile()
    val os = file.outputStream()
    os.write(bs)
    os.close()
    return  File(context.getExternalFilesDir(null).toString() + "/$dir/" + fileName )
  }

fun makeFile(dir: String, fileName: String, context: Context){
    var directory = File(context.getExternalFilesDir(null).toString() + "/$dir/")
    var file = File(context.getExternalFilesDir(null).toString() + "/$dir/" + fileName )
    directory.mkdirs()
    file.createNewFile()
}
fun getFile(dir: String, name: String, context: Context) : ByteArray {
    val fileContents: ByteArray = File(context.getExternalFilesDir(null).toString() + "/$dir/" + name).readBytes()
    return fileContents
}

fun getFileObj(dir: String, name: String, context: Context): File {
    return File(context.getExternalFilesDir(null).toString() + "/$dir/" + name)
}

fun isFile(dir: String, name: String, context: Context) : Boolean {
     return File(context.getExternalFilesDir(null).toString() + "/$dir/" + name).isFile

}






