package com.arelig.dalia.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.arelig.dalia.R
import com.arelig.dalia.sqldatabase.DBHousePlantController

class AddHousePlantActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var spnCategory: Spinner? = null
    private var textMsg: TextView? = null
    private var plantName: EditText? = null
    private var btnAddHousePlant: Button? = null
    private val dbController = DBHousePlantController.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)
        startComponents()
    }

    private fun startComponents() {
        plantName = findViewById(R.id.editPlantName)
        spnCategory = findViewById(R.id.spnCategory)
        startSpinner()
        btnAddHousePlant = findViewById(R.id.btnAddToHouse)
        btnAddHousePlant?.setOnClickListener {
            if (checkName()) {
                addPlant()
            }
        }
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

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        val categories = resources.getStringArray(R.array.category_name)
        textMsg?.text = categories[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun addPlant() {
        val name = plantName?.text.toString()
        val category = spnCategory?.selectedItem.toString()

        DBHousePlantController.addHousePlant(name, category)
        //clear fields
        plantName?.setText("")
        spnCategory?.setSelection(0)
        finish()
    }

    private fun removePlant(id: String) {
        dbController.removeHousePlant(id)
    }

    private fun allPlants() = dbController.getAllHousePlants()


    private fun checkName(): Boolean {
        val value = plantName?.text.toString().isNotEmpty()
        if (value.not()) {
            plantName?.error = getString(R.string.add_Plant_Name_Error)
            return value
        }
        return value
    }
}
