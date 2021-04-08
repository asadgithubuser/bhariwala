package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.FlatAdapter
import com.example.bhariwala.Models.Flat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_flat.*

class FlatActivity : AppCompatActivity() {
    private var propertyId :String? = null
    private var homeLordId :String? = null

    private var flatList: MutableList<Flat>? = null
    private var flatAdapter: FlatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Flat List")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        add_flat_fab_btn22.setOnClickListener {
            var intent = Intent(this, AddFlatActivity::class.java)
            intent.putExtra("propertyId", propertyId)
            startActivity(intent)
        }

        propertyId = intent.getStringExtra("propertyId")
        homeLordId = intent.getStringExtra("homeLordId")

        var fRecyclerView: RecyclerView = findViewById(R.id.flatList_recyclerView)
        fRecyclerView.setHasFixedSize(true)
        fRecyclerView.layoutManager = LinearLayoutManager(this)

        flatList = ArrayList()
        flatAdapter = FlatAdapter(this, flatList as MutableList<Flat>)
        fRecyclerView.adapter = flatAdapter

        retribeFlats(propertyId)

    }

    private fun retribeFlats(propertyId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats")
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    flatList!!.clear()
                    for(flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getPropertyId() == propertyId){
                            flatList!!.add(flatItem!!)
                        }
                    }
                    flatAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}