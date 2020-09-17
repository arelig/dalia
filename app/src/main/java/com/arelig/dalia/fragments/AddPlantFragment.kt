package com.arelig.dalia.fragments

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arelig.dalia.R
import com.arelig.dalia.data.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate.parse
import java.time.format.DateTimeFormatter


class AddPlantFragment : Fragment(), AdapterView.OnItemSelectedListener {
    //view
    private var textMsg: TextView? = null
    private var spnCategory: Spinner? = null
    private var editName: EditText? = null
    private var editLastWatered: DatePicker? = null
    private var btnAddPlant: Button? = null

    //data
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plant, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startViews()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startViews() {
        editName = view?.findViewById(R.id.edit_plant_name)
        editLastWatered = view?.findViewById(R.id.date_picker_last_watered)
        spnCategory = view?.findViewById(R.id.spn_category)
        btnAddPlant = view?.findViewById(R.id.btn_add_plant)
        btnAddPlant?.setOnClickListener {
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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun addPlant() {
        database = FirebaseDatabase.getInstance().reference.child("plants")
        val id = database.push().key
        val name = editName?.text.toString()
        val lastWateredSelected =
            editLastWatered?.dayOfMonth.toString() + "/" + editLastWatered?.month?.inc() + "/" + editLastWatered?.year
        println(lastWateredSelected)
        val categorySelected = spnCategory?.selectedItem.toString()

        //creates a plant instancen and saves it to the database
        val plant = Plant(name, categorySelected, lastWateredSelected)
        if (id != null) {
            database.child(id).setValue(plant)
        }
        //creates a reminder instance with deserialization and saves it to the database
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val localDate = parse(lastWateredSelected, formatter)
        var category: Category? = null
        when (categorySelected) {
            "Flowery" ->
                category = Flowery(localDate)
            "Foliage" ->
                category = Foliage(localDate)
            "Succulent" ->
                category = Succulent(localDate)
            "Cacti" ->
                category = Cacti(localDate)
        }


        val reminder = Reminder(
            name,
            categorySelected,
            category?.wateringFrequency()?.days.toString(),
            category?.lastWatered().toString()
        )
        addReminder(reminder)

        //clear fields
        editName?.setText("")
        spnCategory?.setSelection(0)

        val activity = activity as AppCompatActivity?
        closeKeyboardFromFragment(activity!!)
        Snackbar.make(
            view!!,
            "Plant added to the database",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun addReminder(reminder: Reminder) {
        database = FirebaseDatabase.getInstance().reference.child("reminders")
        val id = database.push().key
        if (id != null) {
            database.child(id).setValue(reminder)
        }
    }

    private fun checkName(): Boolean {
        val value = editName?.text.toString().isNotEmpty()
        if (value.not()) {
            editName?.error = getString(R.string.error_plant_name)
            return value
        }
        return value
    }

    private fun closeKeyboardFromFragment(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        val categories = resources.getStringArray(R.array.category_name)
        textMsg?.text = categories[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}