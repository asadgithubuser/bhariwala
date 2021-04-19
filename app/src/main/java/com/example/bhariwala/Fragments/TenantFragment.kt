package com.example.bhariwala.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.*
import com.example.bhariwala.Adapter.AdsCatAdapter
import com.example.bhariwala.Adapter.AdsCatLocationAdapter
import com.example.bhariwala.Adapter.HomeLordSentAdapter
import com.example.bhariwala.Interfaces.Communicator
import com.example.bhariwala.Models.*
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home_lord.view.*
import kotlinx.android.synthetic.main.fragment_tenant.*
import kotlinx.android.synthetic.main.fragment_tenant.view.*

class TenantFragment : Fragment() {
    private var currentTenantId : FirebaseUser? = null
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
            getHomeLordByTenantName(currentTenantId!!.uid)
        }

        view.tenanti_my_flat_main.setOnClickListener {
            getHomeLordNameByCurrentId(view, currentTenantId!!.uid)
        }

        view.tenanti_my_paybills_main.setOnClickListener {
            startActivity(Intent(context, TenantPayBillsMainAcitivity::class.java))
        }

        view.tenanti_my_expense_main.setOnClickListener {
            startActivity(Intent(context, ExpenseDepositMainAcitivity::class.java))
        }

        showTenantInfo(view)

        //======= received notice
        communicator = activity as Communicator

        view.tenanti_my_notice_main.setOnClickListener {
            communicator.passDataCom(currentTenantId!!.uid)
        }


        //======== ads category by hometype items
        var recyclerView = view.findViewById<RecyclerView>(R.id.ads_catgory_by_rentType_gridView)
        var categoryLayout: LinearLayoutManager = GridLayoutManager(context, 3)
        adsCategoryList = ArrayList()

        //recyclerView.setHasFixedSize(true)
        adsCatAdapter = context?.let { AdsCatAdapter(it, adsCategoryList as MutableList<Ad>) }
        recyclerView.layoutManager = categoryLayout
        recyclerView.adapter = adsCatAdapter
        


        //======== ads category by location items

        var recyclerViewLocation = view.findViewById<RecyclerView>(R.id.ads_catgory_by_location_gridView)
        var categoryLayoutLoc: LinearLayoutManager = GridLayoutManager(context, 3)
        adsCategoryLocationList = ArrayList()

        //recyclerViewLocation.setHasFixedSize(true)
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
                    Picasso.get().load(user.getImage()).into(view.tenant_fragment_profileImg)
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


    private fun getHomeLordNameByCurrentId(view:View, currentTenantId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentTenantId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    view.tntAct_name.text = user!!.getName()
                    getTanantByUserName(user.getUid())
                }
            }
        })
    }
    private fun getTanantByUserName(tenantUserId: String?) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getTenantId().equals(tenantUserId)){
                            getFlatAdsDetailsByFlatId(tenant!!.getFlatId())
                        }
                    }
                }
            }
        })
    }

//    private fun getHomeLordNameById(currentTenantId: String?) {
//        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(currentTenantId!!)
//        userRef.addValueEventListener( object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    var user = snapshot.getValue(User::class.java)
////
////                    tntAct_name.text = user!!.getName()
////                    tntAct_userStatus.text = user!!.getUser()
//
//                    getHomeLordByTenantName(user!!.getName())
//                }
//            }
//        })
//    }



    private fun getHomeLordByTenantName(currentUserIdd: String?) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getTenantId().equals(currentUserIdd)){
                            getHomeLordDetails(tenant!!.getHomeLordId())
                        }
                    }
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
                    var intent = Intent(context, HomeLordProfileActivity::class.java)
                    intent.putExtra("holeLordId", user!!.getUid())
                    startActivity(intent)

                  //  myHomeLordId = user!!.getUid()
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
















}