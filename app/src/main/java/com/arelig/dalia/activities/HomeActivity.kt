package com.arelig.dalia.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arelig.dalia.R
import com.arelig.dalia.data.Plant
import com.arelig.dalia.data.PlantView
import com.arelig.dalia.sqldatabase.DBHousePlantController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter.Mode.IDLE
import eu.davidea.flexibleadapter.SelectableAdapter.Mode.MULTI
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), FlexibleAdapter.OnItemClickListener,
    FlexibleAdapter.OnItemLongClickListener, ActionMode.Callback {
    private val dbController = DBHousePlantController.getInstance(this)
    private var fabAddPlant: FloatingActionButton? = null
    private var mActionMode: ActionMode? = null
    private var mAdapter: FlexibleAdapter<PlantView>? = null

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
            //@todo: startActivity for Result
            startActivity(intent)
        }
    }

    private fun startRecyclerView() {
        rv_home_plant.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_home_plant.setHasFixedSize(true)
        var hpList = getDatabaseList()
        mAdapter = FlexibleAdapter(hpList)
        rv_home_plant.adapter = mAdapter
        mAdapter?.addListener(this)
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

    override fun onItemClick(view: View?, position: Int): Boolean {
        return if (mActionMode != null && position != RecyclerView.NO_POSITION) {
            toggleSelection(position)
            true
        } else {
            false
        }
    }

    override fun onItemLongClick(position: Int) {
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(this)
        }
        toggleSelection(position)
    }

    /**
     * Toggle the selection state of an item.
     * If the item was the last one in the selection and is unselected, the ActionMode
     * is stopped.
     */
    private fun toggleSelection(position: Int) {
        // Mark the position selected
        mAdapter?.toggleSelection(position)
        val count = mAdapter?.selectedItemCount
        if (count == 0) {
            mActionMode!!.finish()
        } else {
            if (count != null) {
                setContextTitle(count)
            }
        }
    }

    private fun setContextTitle(count: Int) {
        mActionMode!!.title =
            "$count " + if (count == 1) getString(R.string.action_selected_one) else getString(
                R.string.action_selected_many
            )
    }

    override fun onCreateActionMode(
        mode: ActionMode,
        menu: Menu?
    ): Boolean {
        mode.menuInflater.inflate(R.menu.options_menu, menu)
        mAdapter?.mode = MULTI
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
                // @todo: i have to put the remove from local and external database
                Toast.makeText(this@HomeActivity, "Delete selected", Toast.LENGTH_SHORT)
                    .show()
                val selectedPos = mAdapter?.selectedPositions
                updateDatabase(selectedPos)
                mode.finish()
                true
            }
            else -> false
        }
    }

    private fun updateDatabase(selectedPos: List<Int>?) {
        val local = getDatabaseList()
        for (i in selectedPos?.indices!!) {
            val pos = local[i]

        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mAdapter?.mode = IDLE
        mActionMode = null
    }

}
