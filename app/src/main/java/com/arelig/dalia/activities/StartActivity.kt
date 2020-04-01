package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.arelig.dalia.R
import com.arelig.dalia.code.DataAdapter
import com.arelig.dalia.code.DataPreference
import com.arelig.dalia.dbmodel.User
import com.arelig.dalia.code.EmailValidation

class StartActivity : AppCompatActivity() {

    private var btnStart: Button? = null
    private var termCond: TextView? = null
    private var aboutMe: TextView? = null
    private var userName: EditText? = null
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        startComponents()
    }

    private fun startComponents() {
        termCond = findViewById(R.id.termCond)
        aboutMe = findViewById(R.id.aboutMe)
        btnStart = findViewById(R.id.btnStart)
        userName = findViewById(R.id.editName)
        userEmail = findViewById(R.id.editEmail)
        userPassword = findViewById(R.id.editPassword)

        val pref: DataPreference = DataAdapter(this)

        termCond?.setOnClickListener {
            val intent = Intent(this, TermCondActivity::class.java)
            startActivity(intent)
        }

        aboutMe?.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        btnStart?.setOnClickListener {
            if (checkData()) {
                pref.setState()
                pref.setData(createUser())
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /*
        @return: true if all the data (evaluated with infix function) are checked
     */
    private fun checkData(): Boolean = checkName() and checkEmail() and checkPassword()

    /*
        @return: true if the field isn't empty, false otherwise
     */
    private fun checkName(): Boolean {
        val value = userName?.text.toString().isNotEmpty()
        if (value.not()) {
            userName?.error = "Who's the plant master?"
            return value
        }
        return value
    }

    /*
        @return: true if the email is valid, false otherwise
     */
    private fun checkEmail(): Boolean {
        val value = EmailValidation.isEmailValid(userEmail?.text.toString())
        if (value.not()) {
            userEmail?.error = "Is it a valid email?"
            return value
        }
        return value
    }

    /*
        @return: true if the password is at least 6 chars long, false otherwise
     */

    private fun checkPassword(): Boolean {
        val value = (userPassword?.text.toString().length >= 6)
        if (value.not()) {
            userPassword?.error = "Password must be at least 6 characters long"
            return value
        }
        return value
    }

    private fun createUser(): User =
        User(
            userName?.text.toString(),
            userEmail?.text.toString(),
            userPassword?.text.toString()
        )
}
