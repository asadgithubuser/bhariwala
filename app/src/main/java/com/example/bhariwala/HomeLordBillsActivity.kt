package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.HomeLordBillsListAdapter
import com.example.bhariwala.Adapter.TenantPayBillsAdapter
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.Models.Tenant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeLordBillsActivity : AppCompatActivity() {

    private var currentUser : FirebaseUser? = null
    private var homelordList: MutableList<Tenant>? = null
    private var homelordAdapter: HomeLordBillsListAdapter? = null

    private var tenantList: MutableList<Tenant>? = null
    private var tenatAdapter: TenantPayBillsAdapter? = null
    private var currentUserStatus: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_lord_bills)

        currentUser = FirebaseAuth.getInstance().currentUser
        currentUserStatus = intent?.getStringExtra("currentUserStatus")

        if(currentUserStatus == "Homelord") {
            var homlordRecyclerView = findViewById<RecyclerView>(R.id.homelord_bills_list_recyclerView)
            homlordRecyclerView.setHasFixedSize(true)
            homlordRecyclerView.layoutManager = LinearLayoutManager(this)

            homelordList = ArrayList()
            homelordAdapter = HomeLordBillsListAdapter(this, homelordList as MutableList<Tenant>)
            homlordRecyclerView.adapter = homelordAdapter

            getReceivedBills()

        }else if(currentUserStatus == "Tenant"){

            var tenantRecyclerView = findViewById<RecyclerView>(R.id.homelord_bills_list_recyclerView)
            tenantRecyclerView.setHasFixedSize(true)
            tenantRecyclerView.layoutManager = LinearLayoutManager(this)

            tenantList = ArrayList()
            tenatAdapter = TenantPayBillsAdapter(this, tenantList as MutableList<PayRent>)
            tenantRecyclerView.adapter = tenatAdapter

            retrieveTenantForHomeLords()
        }
    }


    private fun getReceivedBills() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        val tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getHomeLordId().equals(currentUser!!.uid)){
                            homelordList!!.add(tenant)
                        }
                    }
                    homelordAdapter!!.notifyDataSetChanged()
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