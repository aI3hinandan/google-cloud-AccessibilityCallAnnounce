package com.gearlabs.accessibilitycallannounce
import java.io.*


fun mergeAudio(file1: File, file2:File, outputFile: File) {
    val wavFile1 = file1.absolutePath
    val wavFile2 =  file2.absolutePath
    val fistream1 = FileInputStream(wavFile1)

    val fistream2 = FileInputStream(wavFile2)

    val sistream = SequenceInputStream(fistream1, fistream2)
    val fostream = FileOutputStream(outputFile.absolutePath)

    var temp: Int
    while (sistream.read().also{ temp = it } != -1) {

        fostream.write(temp)
    }
    fostream.close()
    sistream.close()
    fistream1.close()
    fistream2.close()
}

fun mergeAudio(file1: File, file2:File, file3: File, outputFile: File) {
    mergeAudio(file1,file2, outputFile)
    mergeAudio(outputFile,file3,outputFile)
}




