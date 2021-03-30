package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.AdsListByCategoryActivity
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Property
import com.example.bhariwala.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdsCatLocationAdapter(private val mContext: Context, private val mAdsCatList: List<Ad>)
    : RecyclerView.Adapter<AdsCatLocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsCatLocationAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_ads_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mAdsCatList.size
    }

    override fun onBindViewHolder(holder: AdsCatLocationAdapter.ViewHolder, position: Int) {
        var adsCat = mAdsCatList[position]


        holder.itemView.setOnClickListener {
            showAdsByFlatLocation(adsCat.getFlatId())
        }


        getPropertyIdBYFlatId(holder, adsCat.getFlatId())

    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var category_location : TextView = itemView.findViewById(R.id.rfh_category_name)
        var category_countValue : TextView = itemView.findViewById(R.id.rfh_category_countValue)
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

                    var intent = Intent(mContext, AdsListByCategoryActivity::class.java)
                    intent.putExtra("flatLocation", propertyItem!!.getThana())
                    intent.putExtra("isLocation", "yes")
                    mContext.startActivity(intent)
                }
            }
        } )
    }



    private fun getPropertyIdBYFlatId(holder: AdsCatLocationAdapter.ViewHolder, flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().reference.child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getFlatId().equals(flatId)){
                            getFlatAddressFromProperty(holder, flatItem.getPropertyId())
                        }
                    }
                }
            }
        } )
    }


    private fun getFlatAddressFromProperty(holder: AdsCatLocationAdapter.ViewHolder, propertyId: String) {
        var propertyRef = FirebaseDatabase.getInstance().reference.child("Properties").child(propertyId)
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var propertyItem = snapshot.getValue(Property::class.java)
                    holder.category_location.text = propertyItem!!.getThana()

                    countAdsLocation(holder, propertyItem!!.getThana())
                }
            }
        })
    }


    private fun countAdsLocation(holder: AdsCatLocationAdapter.ViewHolder, thana: String){
        var adsRef = FirebaseDatabase.getInstance().reference.child("Ads")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (ads in snapshot.children){
                        var ad = ads.getValue(Ad::class.java)

                        getFlatAddrefdsf(holder, thana, ad!!.getFlatId())
                        Log.d("tttg", "ads")
                    }
                }
            }
        } )
    }

    private fun getFlatAddrefdsf(holder: AdsCatLocationAdapter.ViewHolder, thana: String, flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().reference.child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getFlatId().equals(flatId)){
                            getFlatAddressFrotrere(holder,thana, flatItem.getPropertyId())
                            Log.d("tttg", "flats")
                        }
                    }
                }
            }
        } )
    }

    private fun getFlatAddressFrotrere(holder: AdsCatLocationAdapter.ViewHolder, thana: String, propertyId: String) {
        var count = 0
        var propertyRef = FirebaseDatabase.getInstance().reference.child("Properties")
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for( item in snapshot.children){
                        var propertyItem = item.getValue(Property::class.java)
                        if(propertyItem!!.getPropertyId().equals(propertyId)){

                            holder.category_countValue.text = (count++).toString()
                            Log.d("tttg", "properties")
                        }
                    }

                }
            }
        } )
    }


//
//    private fun countAdsLocation(holder: AdsCatLocationAdapter.ViewHolder, thana: String){
//        var tenantRef = FirebaseDatabase.getInstance().getReference("Properties")
//                .orderByChild("thana")
//                .equalTo(thana)
//        tenantRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//            }
//            override fun onDataChange(snapshot: DataSnapshot) {
//                holder.category_countValue.text = snapshot.childrenCount.toString()
//            }
//
//        })
//    }






}