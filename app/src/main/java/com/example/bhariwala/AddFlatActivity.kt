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

class AddFlatActivity : AppCompatActivity() {

    var persionForSelectItem = arrayOf("Specific Persion", "Not Specific")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flat)

        //setSupportActionBar(property_toolbar_id)
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add Flat")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

//        val autocompleteTextField = persions_for_select_textFeild
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, persionForSelectItem)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        autocompleteTextField.setAdapter(adapter)


    }

}


