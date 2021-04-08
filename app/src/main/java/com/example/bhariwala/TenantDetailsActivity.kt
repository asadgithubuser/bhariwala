package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tenant_details.*

class TenantDetailsActivity : AppCompatActivity() {
   // var tenantUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_details)

        // ==== comes from TenantDetailsActivitty
        var tenantUserId = intent.getStringExtra("tenantUserId")
        retrieveTenantDetails(tenantUserId)

    }

    private fun retrieveTenantDetails(userId: String?) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                   var user = snapshot.getValue(User::class.java)
                       tntd_userName.text = user!!.getName()
                       tntd_profession.text = user!!.getProfession()
                       Picasso.get().load(user.getImage()).into(tenantDetails_image)

                       getTenantID(user.getUid())
                }
            }
        })
    }

    private fun getTenantID(homelordId: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getTenantId().equals(homelordId)){
                            getFlatDetailsByFlatName(tenant.getFlatId())
                        }
                    }
                }
            }
        })
    }

    private fun getFlatDetailsByFlatName(flatId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        flatRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    var flat = snapshot.getValue(Flat::class.java)
                        tntd_flatName.text = flat!!.getFlatName()
                        tntd_buildingName.text = flat!!.getPropertyName()
                        tntd_flatName2.text = flat!!.getFlatName()
                        tntd_flatRent.text = flat!!.getRent()
                        tntd_flat_sf.text = flat!!.getSquareFeet()
                        tntd_total_rooms.text = flat!!.getTotalRooms()
                        tntd_total_baths.text = flat!!.getTotalBaths()
                        tntd_attached_bath.text = flat!!.getAttachedBath()
                        tntd_floor_type.text = flat!!.getFloorType()
                        tntd_availble_persons.text = flat!!.getPersons()
                        tntd_belcony.text = flat!!.getBelcony()
                        tntd_elctricity_bill.text = flat!!.getElectricityBill()
                        tntd_furnist_service.text = flat!!.getFurnistType()

                }
            }
        })
    }






}