package com.arelig.dalia.sharedprefmodel

import com.arelig.dalia.datamodel.User

interface DataPreference {
    fun getData() : User
    fun setData(user: User)
    fun getState() : String?
    fun setState()
}