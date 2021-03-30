package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.AdsDetailsActivity
import com.example.bhariwala.AdsListByCategoryActivity
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdsCatAdapter(private val mContext: Context, private val mAdsCatList: List<Ad>)
    : RecyclerView.Adapter<AdsCatAdapter.ViewHolder>() {

    private var mAdsList: MutableList<Ad>? = null
    private var adsAdapter: AddsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsCatAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_ads_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mAdsCatList.size
    }

    override fun onBindViewHolder(holder: AdsCatAdapter.ViewHolder, position: Int) {
        var adsCat = mAdsCatList[position]

        showCatandCount(holder, adsCat.getRentForWhome())

        holder.itemView.setOnClickListener {
            var intent = Intent(mContext, AdsListByCategoryActivity::class.java)
            intent.putExtra("rentForWhome", adsCat.getRentForWhome())
            intent.putExtra("isLocation", "no")
            mContext.startActivity(intent)
        }

    }




    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var category_name : TextView = itemView.findViewById(R.id.rfh_category_name)
        var category_countValue : TextView = itemView.findViewById(R.id.rfh_category_countValue)
    }

    private fun showCatandCount(holder: AdsCatAdapter.ViewHolder, rentForWhome: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("Ads")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var item = item.getValue(Ad::class.java)
                        if(item!!.getRentForWhome().equals(rentForWhome)){
                            holder.category_name.text = item.getRentForWhome()
                            countCatRentTypes(holder, item.getRentForWhome())
                        }
                    }
                }
            }

        })
    }

     fun countCatRentTypes(holder: AdsCatAdapter.ViewHolder, homeType: String){

        var tenantRef = FirebaseDatabase.getInstance().getReference("Ads")
                .orderByChild("rentForWhome")
                .equalTo(homeType)
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                holder.category_countValue.text = snapshot.childrenCount.toString()
            }

        })
    }






}