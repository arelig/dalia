package com.arelig.dalia.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.arelig.dalia.R

/*
    spn : spinner
 */
class AddPlantActivity : AppCompatActivity() {
    var plantName : EditText? = null
    var spnCategory : Spinner? = null
    var btnAddPlant : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)
    }

    fun startComponents(){
        plantName = findViewById(R.id.editPlantName)
        spnCategory = findViewById(R.id.spnCategory)
        btnAddPlant = findViewById(R.id.btnAddToHouse)

        var categories = resources.getStringArray(R.array.category_name)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spnCategory?.adapter = adapter
    }

    fun addPlant(v: View){

    }
}
