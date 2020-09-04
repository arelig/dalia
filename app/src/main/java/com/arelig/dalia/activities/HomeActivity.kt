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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter.Mode.IDLE
import eu.davidea.flexibleadapter.SelectableAdapter.Mode.MULTI


class HomeActivity : AppCompatActivity(), FlexibleAdapter.OnItemClickListener,
    FlexibleAdapter.OnItemLongClickListener, ActionMode.Callback {
    //views
    private var recyclerViewPlants: RecyclerView? = null
    private var fabAddPlant: FloatingActionButton? = null

    //tools
    private var mActionMode: ActionMode? = null
    private var mAdapter: FlexibleAdapter<PlantView>? = null

    //data
    private var local: MutableList<PlantView>? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        database = FirebaseDatabase.getInstance().reference
        startViews()
        startRecyclerView()
    }


    private fun startRecyclerView() {
        recyclerViewPlants?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPlants?.setHasFixedSize(true)
        startData()
        mAdapter = FlexibleAdapter(local)
        recyclerViewPlants?.adapter = mAdapter
    }

    private fun startViews() {
        recyclerViewPlants = findViewById(R.id.rv_home_plant)
        fabAddPlant = findViewById(R.id.fab_add_houseplant)
        fabAddPlant?.setOnClickListener {
            val intent = Intent(this, AddHousePlantActivity::class.java)
            //@todo: startActivity for Result
            startActivity(intent)
        }
    }

    private fun startData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                updateDatabase(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    private fun updateDatabase(dataSnapshot: DataSnapshot) {
        //clearing the previous plant list
        local = mutableListOf()
        //iterating through all the nodes
        if (dataSnapshot.exists()) {
            for (postSnapshot in dataSnapshot.children) {
                //getting plant
                val plant = postSnapshot.getValue<Plant>(Plant::class.java)
                val id = postSnapshot.key
                val view = PlantView(id, plant)
                //adding plant to the list
                local?.add(view)
            }
        }
        //creating adapter
        mAdapter = FlexibleAdapter(local)
        recyclerViewPlants?.adapter = mAdapter
        mAdapter?.addListener(this)
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
                //cloud
                for (item in mAdapter?.selectedPositions!!) {
                    val toRemove = local?.get(item)?.id
                    database.child(toRemove!!).removeValue()
                }
                mode.finish()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mAdapter?.mode = IDLE
        mActionMode = null
    }
}


