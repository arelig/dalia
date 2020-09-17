package com.arelig.dalia.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arelig.dalia.R
import com.arelig.dalia.data.Reminder
import com.arelig.dalia.data.ReminderView
import com.google.firebase.database.*
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter

class TodayFragment : Fragment(), FlexibleAdapter.OnItemClickListener,
    FlexibleAdapter.OnItemLongClickListener, ActionMode.Callback {

    //view
    private var recyclerViewReminders: RecyclerView? = null

    //tools
    private var mActionMode: ActionMode? = null
    private var mAdapter: FlexibleAdapter<ReminderView>? = null

    //data
    private var local: MutableList<ReminderView>? = null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onPause() {
        super.onPause()
        mActionMode?.finish()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startData()
        startRecyclerView()
    }

    private fun startRecyclerView() {
        recyclerViewReminders = view?.findViewById(R.id.rv_today_reminder)
        recyclerViewReminders?.layoutManager =
            LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        recyclerViewReminders?.setHasFixedSize(true)
        startData()
        mAdapter = FlexibleAdapter(local)
        recyclerViewReminders?.adapter = mAdapter
    }

    private fun startData() {
        database = FirebaseDatabase.getInstance().reference.child("reminders")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                updateDatabase(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateDatabase(dataSnapshot: DataSnapshot) {
        //clearing the previous reminder list
        local = mutableListOf()
        //iterating through all the nodes
        if (dataSnapshot.exists()) {
            for (postSnapshot in dataSnapshot.children) {
                //getting reminder
                val reminder = postSnapshot.getValue<Reminder>(Reminder::class.java)
                val id = postSnapshot.key
                val view = ReminderView(id, reminder)
                //adding reminder to the list
                local?.add(view)
            }
        }
        //creating adapter
        mAdapter = FlexibleAdapter(local)
        recyclerViewReminders?.adapter = mAdapter
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
            mActionMode = (activity as AppCompatActivity?)!!.startSupportActionMode(this)
        }
        toggleSelection(position)
    }


    // Toggle the selection state of an item.
    // If the item was the last one in the selection and is unselected, the ActionMode
    // is stopped.

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
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        mode?.menuInflater?.inflate(R.menu.watered_menu, menu)
        mAdapter?.mode = SelectableAdapter.Mode.MULTI
        mode?.title = "Watered already?"
        return true
    }

    override fun onPrepareActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        return false
    }

    override fun onActionItemClicked(
        mode: ActionMode?,
        item: MenuItem?
    ): Boolean {
        return when (item?.itemId) {
            R.id.check -> {
                Toast.makeText(context!!, "Yay!", Toast.LENGTH_SHORT)
                    .show()
                //cloud
                for (item in mAdapter?.selectedPositions!!) {
                    val toRemove = local?.get(item)?.id
                    database.child(toRemove!!).removeValue()
                }
                //@todo: acordate de actualizar el ultimo riego de todas las plantas que eliminaste...
                mode?.finish()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mAdapter?.mode = SelectableAdapter.Mode.IDLE
        mActionMode = null
    }

}