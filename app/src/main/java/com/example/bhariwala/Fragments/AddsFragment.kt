package com.example.bhariwala.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.AddsAdapter
import com.example.bhariwala.AddAdsActivity
import com.example.bhariwala.AddFlatActivity
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_flat.*
import kotlinx.android.synthetic.main.activity_add_flat.view.*
import kotlinx.android.synthetic.main.fragment_adds.view.*

class AddsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adsAdapter: AddsAdapter? = null
    private var mAdsList: MutableList<Ad>? = null
    private var currentUserId : FirebaseUser? = null

    var status = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_adds, container, false)

        currentUserId = FirebaseAuth.getInstance().currentUser

        view.ads_Addflat_fab_btn.setOnClickListener {
            startActivity(Intent(context, AddAdsActivity::class.java))
        }

//        /// =============== adds fragment operations =============

        recyclerView = view.findViewById(R.id.ads_list_recyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        mAdsList = ArrayList()
        adsAdapter = context?.let{AddsAdapter(it, mAdsList as ArrayList<Ad>)}
        recyclerView?.adapter = adsAdapter



        var userStatus = arguments?.getString("currentUserStatus")
        if(userStatus == "Homelord"){
            retribeAllMyAds()
        }else{
            retribeAllAdsForTenants()
        }




        return view
    }

    private fun retribeAllAdsForTenants() {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Ads")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    mAdsList!!.clear()
                    for (ads in snapshot.children){
                        var adsItem = ads.getValue(Ad::class.java)
                        mAdsList?.add(adsItem!!)
                    }
                    adsAdapter?.notifyDataSetChanged()
                }
            }
        } )
    }
    private fun retribeAllMyAds() {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Ads")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (ads in snapshot.children){
                        var adsItem = ads.getValue(Ad::class.java)
                        if(adsItem!!.getHomeLordId().equals(currentUserId!!.uid)){
                            mAdsList?.add(adsItem!!)
                        }
                    }
                    adsAdapter?.notifyDataSetChanged()
                }
            }
        } )
    }


}