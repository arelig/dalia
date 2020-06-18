package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arelig.dalia.R
import com.arelig.dalia.datamodel.ItemPlant
import com.arelig.dalia.datamodel.ItemPlantAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var btnAddPlant : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var dummyList = generateDummyList(15)
        rv_home_plant.adapter = ItemPlantAdapter(dummyList)
        rv_home_plant.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_home_plant.setHasFixedSize(true)
    }

    private fun generateDummyList(size: Int): List<ItemPlant> {
        val list = ArrayList<ItemPlant>()
        for (i in 0 until size) {
            val item = ItemPlant(R.drawable.plant, "Item $i", "Category")
            list += item
        }
        return list
    }


    override fun onStart() {
        super.onStart()
        startComponents()
    }

    private fun startComponents(){
        btnAddPlant = findViewById(R.id.btn_add_houseplant)
        btnAddPlant?.setOnClickListener{
            val intent = Intent(this, AddHousePlantActivity::class.java)
            startActivity(intent)
        }
    }

    //here i have to display the house plant list
}
