package com.gearlabs.accessibilitycallannounce

fun sortList(listOfNames: MutableList<MutableList<String>>): MutableList<MutableList<String>>{
    val filteredList :MutableList<MutableList<String>> = mutableListOf()
    for(contact in listOfNames){
        if(filteredList.indexOf(contact) == -1) {
            filteredList.add(contact)
        }
    }
    return filteredList
}