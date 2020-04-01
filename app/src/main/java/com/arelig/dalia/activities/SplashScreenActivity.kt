package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arelig.dalia.R
import com.arelig.dalia.code.DataAdapter
import com.arelig.dalia.code.DataPreference

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()

        val pref : DataPreference = DataAdapter(this)
        val activityIntent = if (pref.getState() == "REGISTERED") {
            Intent(this , HomeActivity::class.java)
        }
        else
            Intent(this , StartActivity::class.java)

        startActivity(activityIntent)
        finish()
    }
}
