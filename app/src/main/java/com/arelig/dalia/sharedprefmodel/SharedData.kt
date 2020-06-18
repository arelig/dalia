package com.arelig.dalia.sharedprefmodel

import android.content.Context
import android.content.SharedPreferences
import com.arelig.dalia.R


class SharedData(context : Context) {
    private val prefDataFile = context.getString(R.string.pref_data_file)
    private val pref: SharedPreferences? = context.getSharedPreferences(prefDataFile, Context.MODE_PRIVATE)

    //@todo: modify the primary state NOT-REGISTERED
    fun getVal(key: String?)  = pref?.getString(key,"")

    fun setVal(key: String?, value: String?) {
        val editor = pref?.edit()
        editor?.putString(key,value)
        editor?.apply()
    }
}