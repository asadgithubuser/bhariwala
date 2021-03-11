package com.example.bhariwala

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_flat.*

class AddFlatActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var building_types = arrayOf("Building one", "Building two")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flat)

        //setSupportActionBar(property_toolbar_id)
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add Flat")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val autocompleteTextField = findViewById<AutoCompleteTextView>(R.id.select_building_type)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, building_types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autocompleteTextField.setAdapter(adapter)
        autocompleteTextField.onItemSelectedListener = this


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("item",  building_types[position])
        Toast.makeText(this, "selected: "+ building_types[position], Toast.LENGTH_LONG).show()
    }

}


