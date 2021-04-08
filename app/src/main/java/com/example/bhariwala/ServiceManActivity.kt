package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.AddsAdapter
import com.example.bhariwala.Adapter.FlatAdapter
import com.example.bhariwala.Adapter.ServicemanAdapter
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Serviceman
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_service_man.*

class ServiceManActivity : AppCompatActivity() {


    private var servicemanAdapter: ServicemanAdapter? = null
    private var servicemanList: MutableList<Serviceman>? = null
    private var currentUserId : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_man)

        currentUserId = FirebaseAuth.getInstance().currentUser

        add_serviceman_fab_btn.setOnClickListener {
            startActivity(Intent(this, AddServiceManActivity::class.java))
        }


        var fRecyclerView: RecyclerView = findViewById(R.id.serviceman_list_recyclerView)
        fRecyclerView.setHasFixedSize(true)
        fRecyclerView.layoutManager = LinearLayoutManager(this)

        servicemanList = ArrayList()
        servicemanAdapter = ServicemanAdapter(this, servicemanList as MutableList<Serviceman>)
        fRecyclerView.adapter = servicemanAdapter



    getAllServiceMans()

    }

    private fun getAllServiceMans() {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Servicemans")
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    servicemanList!!.clear()
                    for(flat in snapshot.children){
                        var serviceman = flat.getValue(Serviceman::class.java)
                        if(serviceman!!.getHomelordId().equals(currentUserId!!.uid)){
                            servicemanList!!.add(serviceman)
                        }
                    }
                    servicemanAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}