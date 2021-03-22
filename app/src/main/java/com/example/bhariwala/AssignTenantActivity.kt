package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class AssignTenantActivity : AppCompatActivity() {
    var flatId :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_tenant)

        flatId = intent.getStringExtra("flatId")

      //  retrieveFlatDetails(flatId)

    }


}