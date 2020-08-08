package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arelig.dalia.R
import com.arelig.dalia.datamodel.ItemPlant
import com.arelig.dalia.datamodel.Plant
import com.arelig.dalia.dbmodel.DBHousePlantController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private var hpList: MutableList<IFlexible<*>>? = null
    private val dbController = DBHousePlantController.getInstance(this)
    private var fabAddPlant: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        startComponents()
    }

    private fun startComponents() {
        rv_home_plant.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_home_plant.setHasFixedSize(true)
        hpList = generateHousePlantList()
        val adapter = FlexibleAdapter<IFlexible<*>>(hpList)
        rv_home_plant.adapter = adapter

        fabAddPlant = findViewById(R.id.fab_add_houseplant)
        fabAddPlant?.setOnClickListener {
            val intent = Intent(this, AddHousePlantActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateHousePlantList(): MutableList<IFlexible<*>> {
        val allHousePlant = dbController.getAllHousePlants()
        val listItemPlant: MutableList<IFlexible<*>> = ArrayList()
        var pointer: Plant?

        for (i in 0 until allHousePlant.size) {
            pointer = allHousePlant[i]
            val item = ItemPlant(pointer.name, pointer.name, pointer.category)
            listItemPlant += item
        }
        return listItemPlant
    }
}
