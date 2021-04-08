package com.example.bhariwala.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bhariwala.Adapter.HomeLordSentAdapter
import com.example.bhariwala.Adapter.TenantSendMsgAdapter
import com.example.bhariwala.Models.HomeLordSent
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.TenantSentMsg
import com.example.bhariwala.Models.User
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_received_message.view.*
import kotlinx.android.synthetic.main.fragment_sent_message.view.*


class ReceivedMessageFragment : Fragment() {
    private var firebaseUser: FirebaseUser? = null


    private var homelordMsgList: MutableList<TenantSentMsg>? = null
    private var tenantMsgList: MutableList<HomeLordSent>? = null

    private var tenantMsgAdapter: TenantSendMsgAdapter? = null
    private var homelordMsgAdapter: HomeLordSentAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_received_message, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        checkUserStatus(view)

        return view
    }

    private fun getTeantNameById() {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                     getTenantByName(user!!.getUid())
                }
            }
        })
    }

    private fun getTenantByName(userId: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getTenantId().equals(userId)){
                            getHomeLordMsgByFlatName(tenant.getFlatId())
                        }
                    }
                }
            }
        })
    }

    private fun getHomeLordMsgByFlatName(flatId: String) {
        var hlMsgRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")
        hlMsgRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                   tenantMsgList!!.clear()
                    for(item in snapshot.children){
                        var hlSentMsg = item.getValue(HomeLordSent::class.java)
                        if(hlSentMsg!!.getFlatId().equals(flatId)){
                            tenantMsgList!!.add(hlSentMsg)
                        }
                    }
                    homelordMsgAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }

    private fun fetchMessagesForHomeLord() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("TenantSendMsg")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                   homelordMsgList!!.clear()
                    for(msgitem in snapshot.children){
                        var tenantMsg = msgitem.getValue(TenantSentMsg::class.java)
                        if(tenantMsg!!.getHomeLrdId().equals(firebaseUser!!.uid)){
                            homelordMsgList!!.add(tenantMsg)
                        }
                    }
                    tenantMsgAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }

    private fun checkUserStatus(view: View) {
        var userref = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    if(user!!.getUser() == "Homelord"){
                        homelordMsgList = ArrayList()
                        tenantMsgAdapter = context?.let { TenantSendMsgAdapter(it, homelordMsgList as MutableList<TenantSentMsg>) }
                        view.homlordTenant_received_msg_recyclerView.setHasFixedSize(true)

                        view.homlordTenant_received_msg_recyclerView.layoutManager = LinearLayoutManager(context)
                        view.homlordTenant_received_msg_recyclerView.adapter = tenantMsgAdapter


                        fetchMessagesForHomeLord()
                    }else if(user.getUser() == "Tenant"){
                        tenantMsgList = ArrayList()
                        homelordMsgAdapter = context?.let { HomeLordSentAdapter(it, tenantMsgList as MutableList<HomeLordSent>) }
                        view.homlordTenant_received_msg_recyclerView.setHasFixedSize(true)

                        view.homlordTenant_received_msg_recyclerView.layoutManager = LinearLayoutManager(context)
                        view.homlordTenant_received_msg_recyclerView.adapter = homelordMsgAdapter

                        getTeantNameById()
                    }
                }
            }
        })
    }

}