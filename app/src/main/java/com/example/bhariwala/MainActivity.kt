package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tenant_details_btn.setOnClickListener{
            startActivity(Intent(this, TenantActivity::class.java))
        }
        properties_area_btn.setOnClickListener{
            var intent = Intent(this, PropertyAcitivity::class.java)
            startActivity(intent)
        }
        parcel_btn.setOnClickListener {
            startActivity(Intent(this, AddFlatActivity::class.java))
        }

    }


}