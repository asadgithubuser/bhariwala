package com.example.bhariwala.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.AdsCatAdapter
import com.example.bhariwala.Adapter.AdsCatLocationAdapter
import com.example.bhariwala.Adapter.HomeLordSentAdapter
import com.example.bhariwala.CarParkingActivity
import com.example.bhariwala.CarParkingListActivity
import com.example.bhariwala.HomeLordProfileActivity
import com.example.bhariwala.Interfaces.Communicator
import com.example.bhariwala.Models.*
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_security_guard.view.*

class SecurityGuardFragment : Fragment() {
    private var currentUserId : FirebaseUser? = null
    var myHomeLordId = ""
    private lateinit var communicator: Communicator

    private var catList: ArrayList<String>? = null
    private var catListByRT: ArrayList<String>? = null

    private var adsCategoryList: MutableList<Ad>? = null
    private var adsCatAdapter: AdsCatAdapter? = null

    private var adsCategoryLocationList: MutableList<Ad>? = null
    private var adsCatLocationAdapter: AdsCatLocationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_security_guard, container, false)

        currentUserId = FirebaseAuth.getInstance().currentUser


        view.homeLord_btn_from_cecutirtyGuard.setOnClickListener {
            getHomelordFromcecutirtyGuard(currentUserId!!.uid)
        }

        view.cecutirtyGuard_carparking_btn.setOnClickListener {
            var intent = Intent(context, CarParkingListActivity::class.java)
            intent.putExtra("homelordId", myHomeLordId)
            startActivity(intent)
        }


        //======== ads categoryby hometype items
        var recyclerView = view.findViewById<RecyclerView>(R.id.ads_catgory_by_rentType_gridView)
        var categoryLayout: LinearLayoutManager = GridLayoutManager(context, 3)
        adsCategoryList = ArrayList()

        recyclerView.setHasFixedSize(true)
        adsCatAdapter = context?.let { AdsCatAdapter(it, adsCategoryList as MutableList<Ad>) }
        recyclerView.layoutManager = categoryLayout
        recyclerView.adapter = adsCatAdapter


        //======== ads category by location items
        var recyclerViewLocation = view.findViewById<RecyclerView>(R.id.ads_catgory_by_location_gridView)
        var categoryLayoutLoc: LinearLayoutManager = GridLayoutManager(context, 3)
        adsCategoryLocationList = ArrayList()

        recyclerViewLocation.setHasFixedSize(true)
        adsCatLocationAdapter = context?.let { AdsCatLocationAdapter(it, adsCategoryLocationList as MutableList<Ad>) }
        recyclerViewLocation.layoutManager = categoryLayoutLoc
        recyclerViewLocation.adapter = adsCatLocationAdapter


        catList = ArrayList()
        catListByRT = ArrayList()


        //======= received notice
        communicator = activity as Communicator



        showTenantInfo(view)

        getAdsCatByTentType()
        getAdsCatByTentTypeLoc()

        return view
    }

    private fun showTenantInfo(view: View) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentUserId!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    view.cecutirtyGuard_name.text = user!!.getName()
                    view.cecutirtyGuard_userStatus.text = user!!.getUser()
                }
            }
        })
    }
    private fun getAdsCatByTentType() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Ads")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var ads = item.getValue(Ad::class.java)
                        if(catListByRT!!.contains(ads!!.getRentForWhome()) == false){
                            catListByRT!!.add(ads.getRentForWhome())
                            adsCategoryList!!.add(ads!!)
                        }
                    }
                    adsCatAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }


    private fun getAdsCatByTentTypeLoc() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Ads")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var ad = item.getValue(Ad::class.java)

                        getPropertyIdBYFlatId(ad!!, ad.getFlatId())

                    }
                }
            }
        })
    }


    private fun getPropertyIdBYFlatId(ad: Ad, flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().reference.child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getFlatId().equals(flatId)){
                            getFlatAddressFromProperty(ad, flatItem.getPropertyId())
                        }
                    }
                }
            }
        } )
    }

    private fun getFlatAddressFromProperty(ad: Ad, propertyId: String) {
        var propertyRef = FirebaseDatabase.getInstance().reference.child("Properties").child(propertyId)
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var propertyItem = snapshot.getValue(Property::class.java)
                    if(catList!!.contains(propertyItem!!.getThana()) == false){
                        catList!!.add(propertyItem!!.getThana())
                        adsCategoryLocationList!!.add(ad)
                    }
                    adsCatLocationAdapter!!.notifyDataSetChanged()
                }
            }
        } )
    }
    private fun getHomelordFromcecutirtyGuard(currentTenantId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentTenantId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    getHomeLordByServicemanId(user!!.getUid())
                }
            }
        })
    }



    private fun getHomeLordByServicemanId(servicemanId: String?) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Servicemans").child(servicemanId!!)
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var serviceman = snapshot.getValue(Serviceman::class.java)
                    getHomeLordDetails(serviceman!!.getHomelordId())
                }
            }
        })
    }

    private fun getHomeLordDetails(homeLordId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(homeLordId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    myHomeLordId = user!!.getUid()
                    var intent = Intent(context, HomeLordProfileActivity::class.java)
                    intent.putExtra("holeLordId", user.getUid())
                    startActivity(intent)

                }
            }
        })
    }



}