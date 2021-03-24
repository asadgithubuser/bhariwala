package com.example.bhariwala.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bhariwala.HomeLordProfileActivity
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_lord_profile.*
import kotlinx.android.synthetic.main.fragment_tenant.*
import kotlinx.android.synthetic.main.fragment_tenant.view.*

class TenantFragment : Fragment() {
    private var currentTenantId : FirebaseUser? = null
    private var myHomeLordId : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_tenant, container, false)

        currentTenantId = FirebaseAuth.getInstance().currentUser

        view.homeLord_btn_from_tenant.setOnClickListener {
            getHomeLordNameById(currentTenantId!!.uid)
        }











        return view
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