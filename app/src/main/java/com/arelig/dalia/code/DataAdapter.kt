package com.arelig.dalia.code

import android.content.Context
import com.arelig.dalia.dbmodel.User

class DataAdapter(context: Context) : DataPreference {

    var sp = SharedData(context)

    override fun setData(user: User) {
        sp.setVal("user_name", user.name)
        sp.setVal("user_email", user.email)
        sp.setVal("user_password", user.password)
    }

    /*
        @return: User with saved data if registered, User with dummy data if not.
     */
    override fun getData(): User = if (sp.getVal("state") == "REGISTERED")
        User(
            sp.getVal("user_name"),
            sp.getVal("user_email"),
            sp.getVal("user_password")
        )
    else User()

    override fun getState(): String? {
        return sp.getVal("state")
    }

    override fun setState() {
        if (sp.getVal("state") == "")
            sp.setVal("state", "REGISTERED")
    }
}