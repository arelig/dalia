package com.arelig.dalia.code

import com.arelig.dalia.dbmodel.User

interface DataPreference {
    fun getData() : User
    fun setData(user: User)
    fun getState() : String?
    fun setState()
}