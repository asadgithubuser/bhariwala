package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_lord_profile.*

class HomeLordProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_lord_profile)


        var homeLordId = intent.getStringExtra("holeLordId")

        getHomeLordDetails(homeLordId)

    }


    private fun getHomeLordDetails(homeLordId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(homeLordId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    hldr_name.text = user!!.getName()
                    hldr_profession.text = user!!.getProfession()
                }
            }
        })
    }

}