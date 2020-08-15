package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import com.arelig.dalia.R
import com.arelig.dalia.data.Plant
import com.arelig.dalia.data.PlantView
import com.arelig.dalia.sqldatabase.DBHousePlantController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter.Mode
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private val dbController = DBHousePlantController.getInstance(this)
    private var fabAddPlant: FloatingActionButton? = null
    private var mActionMode: ActionMode? = null
    private var hello_msg: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        startComponents()
        startRecyclerView()
    }

    private fun startComponents() {
        fabAddPlant = findViewById(R.id.fab_add_houseplant)
        fabAddPlant?.setOnClickListener {
            val intent = Intent(this, AddHousePlantActivity::class.java)
            startActivity(intent)
        }


    }

    private fun startRecyclerView() {
        rv_home_plant.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_home_plant.setHasFixedSize(true)
        var hpList = getDatabaseList()
        val adapter = FlexibleAdapter(hpList)
        rv_home_plant.adapter = adapter
        adapter.mode = Mode.SINGLE
        adapter.addListener(FlexibleAdapter.OnItemLongClickListener {
            if (mActionMode != null) {
                false
            }
            mActionMode = startSupportActionMode(mActionModeCallback)
            true
        })
    }

    private val mActionModeCallback: ActionMode.Callback =
        object : ActionMode.Callback {
            override fun onCreateActionMode(
                mode: ActionMode,
                menu: Menu?
            ): Boolean {
                mode.menuInflater.inflate(R.menu.options_menu, menu)
                mode.title = "Choose an option"
                return true
            }

            override fun onPrepareActionMode(
                mode: ActionMode?,
                menu: Menu?
            ): Boolean {
                return false
            }

            override fun onActionItemClicked(
                mode: ActionMode,
                item: MenuItem
            ): Boolean {
                return when (item.itemId) {
                    R.id.share -> {
                        Toast.makeText(this@HomeActivity, "Share selected", Toast.LENGTH_SHORT)
                            .show()
                        mode.finish()
                        true
                    }
                    R.id.delete -> {
                        Toast.makeText(this@HomeActivity, "Delete selected", Toast.LENGTH_SHORT)
                            .show()
                        mode.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                mActionMode = null
            }
        }

    private fun getDatabaseList(): MutableList<PlantView> {
        val list: MutableList<PlantView> = ArrayList()
        val databasePlants = dbController.getAllHousePlants()
        var pointer: Plant
        for (element in databasePlants) {
            pointer = element
            list.add(PlantView(pointer))
        }
        return list.toMutableList()
    }


}
