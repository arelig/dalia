package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arelig.dalia.R
import com.arelig.dalia.datamodel.ItemPlant
import com.arelig.dalia.datamodel.ItemPlantAdapter
import com.arelig.dalia.datamodel.Plant
import com.arelig.dalia.dbmodel.DBHousePlantController
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var btnAddPlant: Button? = null
    private lateinit var hpList: List<ItemPlant>
    private val dbController = DBHousePlantController.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        startComponents()
    }

    private fun startComponents() {
        btnAddPlant = findViewById(R.id.btn_add_houseplant)
        btnAddPlant?.setOnClickListener {
            val intent = Intent(this, AddHousePlantActivity::class.java)
            startActivity(intent)
        }

        rv_home_plant.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_home_plant.setHasFixedSize(true)
        hpList = generateHousePlantList()
        rv_home_plant.adapter = ItemPlantAdapter(hpList)
    }

    private fun generateHousePlantList(): ArrayList<ItemPlant> {
        val allHousePlant = dbController.getAllHousePlants()
        val listItemPlant: ArrayList<ItemPlant> = ArrayList()
        var pointer: Plant?

        if (allHousePlant != null) {
            for (i in 0 until allHousePlant.size) {
                pointer = allHousePlant[i]
                val item = ItemPlant(R.drawable.plant, pointer.name, pointer.category)
                listItemPlant += item
            }
        }

        return listItemPlant
    }
}
