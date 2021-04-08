package com.example.bhariwala

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.TenantsListHLAdapter
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Tenant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeLordTenantsActivity : AppCompatActivity() {
    private var currentUser : FirebaseUser? = null
    private var tenantList: MutableList<Tenant>? = null
    private var tenatAdapter: TenantsListHLAdapter? = null

    var isListForProperty : String? = null
    var propertyId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_lord_tenants)

        currentUser = FirebaseAuth.getInstance().currentUser

        //came from PropertyAdapter
        isListForProperty = intent?.getStringExtra("isListForProperty")
        propertyId = intent?.getStringExtra("propertyName")


        var tenantRecyclerView = findViewById<RecyclerView>(R.id.tenantListForHL_recyclerView)
        tenantRecyclerView.setHasFixedSize(true)
        tenantRecyclerView.layoutManager = LinearLayoutManager(this)

        tenantList = ArrayList()
        tenatAdapter = TenantsListHLAdapter(this, tenantList as MutableList<Tenant>)
        tenantRecyclerView.adapter = tenatAdapter

        if(isListForProperty == "1") {
            onlyListForProperty()
        }else{
            checkHaveTenantOrNot()
        }



    }

    private fun onlyListForProperty() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    tenantList!!.clear()
                    for(item in snapshot.children){
                        val tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getHomeLordId().equals(currentUser!!.uid) && tenant.getPropertyId().equals(propertyId)){
                            tenantList!!.add(tenant)
                        }
                    }
                    tenatAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }

    private fun checkHaveTenantOrNot() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
                .orderByChild("homeLordId")
                .equalTo(currentUser!!.uid)
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount <= 0){
                    //finish()

                    var alertDialog = AlertDialog.Builder(this@HomeLordTenantsActivity)
                    alertDialog.setTitle("Tenant Add")
                    alertDialog.setMessage("You have  not added tenant yet. please add tenant first.")
                    alertDialog.setCancelable(false)
                    alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener{
                        dialog, id ->  finish()
                    })
                    alertDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener{
                        dialog, id ->  dialog.cancel()
                    })
                    val alert = alertDialog.create()
                    alert.show()

                }else{
                    retrieveTenantForHomeLords()
                }
            }
        })
    }

    private fun retrieveTenantForHomeLords() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    tenantList!!.clear()
                    for(item in snapshot.children){
                        val tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getHomeLordId().equals(currentUser!!.uid)){
                            tenantList!!.add(tenant)
                        }
                    }
                    tenatAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }
}