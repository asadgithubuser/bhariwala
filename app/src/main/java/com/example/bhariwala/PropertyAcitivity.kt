package com.example.bhariwala

import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.PropertyAdapter
import com.example.bhariwala.Models.Property
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.property_item.*

class PropertyAcitivity : AppCompatActivity() {
    private var mPropertyList: MutableList<Property>? = null
    private var propertyAdapter: PropertyAdapter? = null
    private var firebaseUser : FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        //setSupportActionBar(property_toolbar_id)
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Property List")
        }

        var recyclerView = findViewById<RecyclerView>(R.id.property_list_recylerView)
        recyclerView.setHasFixedSize(true)

        var propertyLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = propertyLayout

        mPropertyList = ArrayList()
        propertyAdapter = PropertyAdapter(this, mPropertyList as ArrayList<Property> )
        recyclerView.adapter = propertyAdapter


        retribeAllProperty()


        add_property_fab_btn2.setOnClickListener {
            startActivity(Intent(this, AddPropertyActivity::class.java))
        }




    }

    private fun retribeAllProperty() {
        var propertyReff = FirebaseDatabase.getInstance().reference.child("Properties")
        propertyReff.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (proItem in snapshot.children){
                        var property = proItem.getValue(Property::class.java)
                        if (property!!.getHomeLordId() == firebaseUser!!.uid){
                            mPropertyList!!.add(property!!)
                        }
                    }
                    propertyAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }


//    private fun showSendMessagePopUp() {
//        var dialog = AlertDialog.Builder(this)
//        dialog.setTitle("Send Message")
//        var inflater = layoutInflater.inflate(R.layout.send_message_popup, null)
//        dialog.setView(inflater)
//        dialog.show()
//    }

}