package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.activity_ads_details.*

class HomeLordTenantsActivity : AppCompatActivity() {

    private var currentUser : FirebaseUser? = null
    private var tenantList: MutableList<Tenant>? = null
    private var tenatAdapter: TenantsListHLAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_lord_tenants)

        currentUser = FirebaseAuth.getInstance().currentUser

        var tenantRecyclerView = findViewById<RecyclerView>(R.id.tenantListForHL_recyclerView)
        tenantRecyclerView.setHasFixedSize(true)
        tenantRecyclerView.layoutManager = LinearLayoutManager(this)

        tenantList = ArrayList()
        tenatAdapter = TenantsListHLAdapter(this, tenantList as MutableList<Tenant>)
        tenantRecyclerView.adapter = tenatAdapter







        retrieveTenantForHomeLords()



    }

    private fun retrieveTenantForHomeLords() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
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