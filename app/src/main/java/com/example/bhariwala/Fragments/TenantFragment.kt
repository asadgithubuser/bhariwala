package com.example.bhariwala.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.bhariwala.FlatAdsDetailsActivity
import com.example.bhariwala.HomeLordProfileActivity
import com.example.bhariwala.Interfaces.Communicator
import com.example.bhariwala.Models.*
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tenant.*
import kotlinx.android.synthetic.main.fragment_tenant.view.*

class TenantFragment : Fragment() {
    private var currentTenantId : FirebaseUser? = null
    private var myHomeLordId : String? = null
    private var tenantMsgList: MutableList<HomeLordSent>? = null
    private var homelordMsgAdapter: HomeLordSentAdapter? = null
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
        var view = inflater.inflate(R.layout.fragment_tenant, container, false)

        currentTenantId = FirebaseAuth.getInstance().currentUser

        view.homeLord_btn_from_tenant.setOnClickListener {
            getHomeLordNameById(currentTenantId!!.uid)
        }

        view.tenanti_my_flat_main.setOnClickListener {
            getHomeLordNameByCurrentId(currentTenantId!!.uid)
        }

        showTenantInfo(view)

        //======= received notice
        communicator = activity as Communicator

        view.tenanti_my_notice_main.setOnClickListener {
            communicator.passDataCom(currentTenantId!!.uid)
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

        getAdsCatByTentType()
        getAdsCatByTentTypeLoc()

        return view
    }

    private fun showTenantInfo(view: View) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentTenantId!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    view.tntAct_name.text = user!!.getName()
                    view.tntAct_userStatus.text = user!!.getUser()
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


    private fun getHomeLordNameByCurrentId(currentTenantId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentTenantId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    tntAct_name.text = user!!.getName()

                    getTanantByUserName(user!!.getName())
                }
            }
        })
    }

    private fun getHomeLordNameById(currentTenantId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentTenantId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    tntAct_name.text = user!!.getName()
                    tntAct_userStatus.text = user!!.getUser()

                    getHomeLordByTenantName(user!!.getName())
                }
            }
        })
    }

    private fun getTanantByUserName(tenantName: String?) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getTenantUserName().equals(tenantName)){
                            getFlatByFlatName(tenant!!.getFlatName())
                        }
                    }
                }
            }
        })
    }

    private fun getHomeLordByTenantName(tenantName: String?) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getTenantUserName().equals(tenantName)){
                            getHomeLordDetails(tenant!!.getHomeLordId())
                        }
                    }
                }
            }
        })
    }

    private fun getFlatByFlatName(flatName: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats")
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var flat = item.getValue(Flat::class.java)
                        if(flat!!.getFlatName().equals(flatName)){
                            getFlatAdsDetailsByFlatId(flat!!.getFlatId())
                            Log.d("ckecked", " one44")
                        }
                    }
                }
            }
        })
    }

    private fun getFlatAdsDetailsByFlatId(flatId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)
                    var intent = Intent(context, FlatAdsDetailsActivity::class.java)
                    intent.putExtra("flatId", flat!!.getFlatId())
                    startActivity(intent)

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
                    intent.putExtra("holeLordId", user!!.getUid())
                    startActivity(intent)

                }
            }
        })
    }















}