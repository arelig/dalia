package com.arelig.dalia.fragments

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arelig.dalia.R
import com.arelig.dalia.data.Plant
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddPlantFragment : Fragment(), AdapterView.OnItemSelectedListener {
    //view
    private var spnCategory: Spinner? = null
    private var textMsg: TextView? = null
    private var plantName: EditText? = null
    private var btnAddHousePlant: Button? = null

    //data
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startData()
        startViews()
    }

    private fun startData() {
        database = FirebaseDatabase.getInstance().reference
    }

    private fun startViews() {
        plantName = view?.findViewById(R.id.edit_plant_name)
        spnCategory = view?.findViewById(R.id.spn_category)
        btnAddHousePlant = view?.findViewById(R.id.btn_add_plant)
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
                context!!,
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
        val activity = activity as AppCompatActivity?
        closeKeyboardFromFragment(activity!!)
        Snackbar.make(
            view!!,
            "Plant added to the database",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun closeKeyboardFromFragment(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
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