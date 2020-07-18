package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.arelig.dalia.R
import com.arelig.dalia.sharedprefmodel.DataAdapter
import com.arelig.dalia.sharedprefmodel.DataPreference

class SplashScreenActivity : AppCompatActivity() {
    private var btnGetStarted: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        startComponents()

        val pref: DataPreference = DataAdapter(this)
        if (pref.getState() == "REGISTERED") {
            Intent(this, HomeActivity::class.java)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startComponents() {
        btnGetStarted = findViewById(R.id.btnGetStarted)

        btnGetStarted?.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
