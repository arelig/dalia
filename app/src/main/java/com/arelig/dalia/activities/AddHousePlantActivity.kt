package com.arelig.dalia.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.arelig.dalia.R
import com.arelig.dalia.data.Plant
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddHousePlantActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    //ui
    private var spnCategory: Spinner? = null
    private var textMsg: TextView? = null
    private var plantName: EditText? = null
    private var btnAddHousePlant: Button? = null

    //data
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)
        startData()
        startUi()
    }

    private fun startData() {
        database = FirebaseDatabase.getInstance().reference
    }

    private fun startUi() {
        plantName = findViewById(R.id.editPlantName)
        spnCategory = findViewById(R.id.spnCategory)
        btnAddHousePlant = findViewById(R.id.btnAddToHouse)
        btnAddHousePlant?.setOnClickListener {
            if (checkName()) {
                addPlant()
            }
        }
        startSpinner()
    }

    private fun startSpinner() {
        val categories = resources.getStringArray(R.array.category_name)
        spnCategory?.onItemSelectedListener = this
        if (spnCategory != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categories
            )
            spnCategory?.adapter = adapter
        }
    }


    private fun addPlant() {
        val name = plantName?.text.toString()
        val category = spnCategory?.selectedItem.toString()
        val id = database.push().key
        //creates a plant object
        val plant = Plant(name, category)
        if (id != null) {
            database.child(id).setValue(plant)
        }
        //clear fields
        plantName?.setText("")
        spnCategory?.setSelection(0)
        Toast.makeText(this, "Plant added", Toast.LENGTH_SHORT).show()
    }


    private fun checkName(): Boolean {
        val value = plantName?.text.toString().isNotEmpty()
        if (value.not()) {
            plantName?.error = getString(R.string.add_Plant_Name_Error)
            return value
        }
        return value
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        val categories = resources.getStringArray(R.array.category_name)
        textMsg?.text = categories[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}
