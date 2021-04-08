package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.CategoryAdsAdapter
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Property
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdsListByCategoryActivity : AppCompatActivity() {

    var rentForWhome: String? = null
    var isSearchByLocation: String? = null
    var flatLocation: String? = null

    private var categoryAdsList : MutableList<Ad>? = null
    private var categoryAdsAdapter : CategoryAdsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_list_by_category)

        rentForWhome = intent?.getStringExtra("rentForWhome")
        flatLocation = intent?.getStringExtra("flatLocation")
        isSearchByLocation = intent?.getStringExtra("isLocation")

        var recyclerView = findViewById<RecyclerView>(R.id.add_list_by_category_recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        categoryAdsList = ArrayList()
        categoryAdsAdapter = CategoryAdsAdapter(this, categoryAdsList as MutableList<Ad>)
        recyclerView.adapter = categoryAdsAdapter

        if(isSearchByLocation == "yes"){
            getAllAdsByFlatLocation(flatLocation!!)
        }else{
            getAllAdsByPassingRentType(rentForWhome!!)
        }

    }

    private fun getAllAdsByFlatLocation(flatLocation: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("")
    }


    private fun showAdsByFlatLocation(flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().reference.child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getFlatId().equals(flatId)){
                            getFlatAddressFromProperty22(flatItem.getPropertyId())
                        }
                    }
                }
            }
        } )
    }


    private fun getFlatAddressFromProperty22(propertyId: String) {
        var propertyRef = FirebaseDatabase.getInstance().reference.child("Properties").child(propertyId)
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var propertyItem = snapshot.getValue(Property::class.java)

                }
            }
        } )
    }





    private fun getAllAdsByPassingRentType(rentForWhome: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("Ads")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    categoryAdsList!!.clear()
                    for (item in snapshot.children){
                        var item = item.getValue(Ad::class.java)
                        if(item!!.getRentForWhome().equals(rentForWhome)){
                            categoryAdsList!!.add(item)
                        }
                    }
                    categoryAdsAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }


}