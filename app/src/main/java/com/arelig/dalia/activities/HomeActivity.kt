package com.arelig.dalia.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.arelig.dalia.R

class HomeActivity : AppCompatActivity() {

    var btnAddPlant : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        startComponents()
    }

    private fun startComponents(){
        btnAddPlant = findViewById(R.id.btnAddPlant)

        btnAddPlant?.setOnClickListener{
            val intent = Intent(this, AddPlantActivity::class.java)
            startActivity(intent)
        }
    }
}
