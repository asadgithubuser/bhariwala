package com.example.bhariwala

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_flat.*
import kotlinx.android.synthetic.main.activity_add_property.*

class AddPropertyActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    var spinner: Spinner? = null

    var divisions = arrayOf("dhaka", "khulna", "borishal", "rajshahi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        //setSupportActionBar(property_toolbar_id)
//        var actionBar = getSupportActionBar()
//        if(actionBar != null){
//            actionBar.setTitle("Add Property")
//            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left)
//        }

        var actionbar = supportActionBar
        if(actionbar != null) {
            supportActionBar!!.title = "go home"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        spinner = findViewById(R.id.district_fds)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisions)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(arrayAdapter)
        spinner!!.setOnItemSelectedListener(this)



    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this.applicationContext, "Selected: "+divisions[position], Toast.LENGTH_LONG).show()
    }
}

