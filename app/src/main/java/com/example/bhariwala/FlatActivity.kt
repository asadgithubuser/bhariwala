package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_flat.*

class FlatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Flat List")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        add_flat_fab_btn.setOnClickListener {
            startActivity(Intent(this, AddFlatActivity::class.java))
        }


    }
}