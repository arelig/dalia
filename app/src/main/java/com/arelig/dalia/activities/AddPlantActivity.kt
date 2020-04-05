package com.arelig.dalia.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.arelig.dalia.R

/*
    spn : spinner
 */
class AddPlantActivity : AppCompatActivity() {
    var plantName: EditText? = null
    var btnAddPlant: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)
        startComponents()
    }

    private fun startComponents() {
        plantName = findViewById(R.id.editPlantName)
        btnAddPlant = findViewById(R.id.btnAddToHouse)
        startSpinner()
    }

    private fun startSpinner() {
        var categories = resources.getStringArray(R.array.category_name)
        val spnCategory = findViewById<Spinner>(R.id.spnCategory)
        if (spnCategory != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
            spnCategory?.adapter = adapter

            spnCategory.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    //does something when the item is selected
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        fun addPlant(v: View) {

        }
    }
}
