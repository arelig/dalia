package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arelig.dalia.R
import com.arelig.dalia.datamodel.ItemPlant
import com.arelig.dalia.datamodel.ItemPlantAdapter
import com.arelig.dalia.datamodel.Plant
import com.arelig.dalia.dbmodel.DBHousePlantController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var fabAddPlant: FloatingActionButton? = null
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
        fabAddPlant = findViewById(R.id.fab_add_houseplant)
        fabAddPlant?.setOnClickListener {

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
