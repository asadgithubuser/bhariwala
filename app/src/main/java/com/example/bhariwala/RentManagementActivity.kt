package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RentManagementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_management)

        var actionBar = getSupportActionBar()
        if( actionBar != null){
            actionBar.setTitle("Rent List Info")
        }
    }
}