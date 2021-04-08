package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.bhariwala.Models.AccountDetail
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_lord_profile.*

class HomeLordProfileActivity : AppCompatActivity() {
    var aboutUrSelf : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_lord_profile)

        aboutUrSelf = findViewById(R.id.hldr_aboutUrSelf)
        var homeLordId = intent?.getStringExtra("holeLordId")

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
                    Picasso.get().load(user.getImage()).into(hldr_Profile_image)

                    getHomeLordFromDeetails(user!!.getUid())
                }
            }
        })
    }


    private fun getHomeLordFromDeetails(homeLordId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("AccountDetails")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for( item in snapshot.children){
                        var accountd = item.getValue(AccountDetail::class.java)
                        if(accountd!!.getUserId().equals(homeLordId)){
                            hldr_phone.text = accountd!!.getPhone()
                            aboutUrSelf!!.setText(accountd!!.getAboutUser())
                            hldr_address.text = accountd!!.getAddress()
                            hldr_thana.text = accountd!!.getThana()+", "+accountd.getSection()
                            hldr_district.text = accountd!!.getCity()
                            hldr_division.text = accountd!!.getDivisions()
                        }

                    }
                }
            }
        })
    }

}