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
import com.example.bhariwala.MessageMainAcitivity
import com.example.bhariwala.Models.HomeLordSent
import com.example.bhariwala.Models.TenantSentMsg
import com.example.bhariwala.Models.User
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_sent_message.view.*

class SentMessageFragment : Fragment() {
//    private var homlordSentMsgList: MutableList<HomeLordSent>? = null
//    private var hlSentAdapter : HomeLordSentAdapter? = null
//
//    private var tenantSentMsgList : MutableList<TenantSentMsg>? = null
//    private var tenantSndMsgAdapter: TenantSendMsgAdapter? = null

     var status  = ""
    private var firebaseUser: FirebaseUser? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sent_message, container, false)

//       var currentUserStatus22 = arguments?.getString("userStatuss")
//        toast("mmain act pass data -> "+currentUserStatus22)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        Log.d("vvv", status)
        toast("some -> "+status)
//        if(currentUserStatus == "Homelord") {
//            toast("hl -> "+currentUserStatus)
//            homlordSentMsgList = ArrayList()
//            hlSentAdapter = context?.let { HomeLordSentAdapter(it, homlordSentMsgList as MutableList<HomeLordSent>) }
//            view.homlord_sent_msg_recyclerView.setHasFixedSize(true)
//
//            view.homlord_sent_msg_recyclerView.layoutManager = LinearLayoutManager(context)
//            view.homlord_sent_msg_recyclerView.adapter = hlSentAdapter
//        }else if(currentUserStatus == "Tenant"){
//            toast("tnt -> "+currentUserStatus)
//            tenantSentMsgList = ArrayList()
//            tenantSndMsgAdapter = context?.let { TenantSendMsgAdapter(it, tenantSentMsgList as MutableList<TenantSentMsg>) }
//            view.homlord_sent_msg_recyclerView.setHasFixedSize(true)
//
//            view.homlord_sent_msg_recyclerView.layoutManager = LinearLayoutManager(context)
//            view.homlord_sent_msg_recyclerView.adapter = tenantSndMsgAdapter
//        }

//        if(currentUserStatus == "Homelord"){
//            retrieveAllHLMsgList()
//        }else if(currentUserStatus == "Tenant"){
//            tenantSentsAllMessage()
//        }

       // checkUserCurrentStatus()

        cName()
        return view
    }

    private fun cName() {
        status = "something value here"
    }

    fun toast(s: String){
        Toast.makeText(context, s, Toast.LENGTH_LONG).show()
    }
//    private fun tenantSentsAllMessage() {
//        var tenantSMRef = FirebaseDatabase.getInstance().reference.child("TenantSendMsg")
//        tenantSMRef.addValueEventListener( object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for (item in snapshot.children){
//                        var tenant = item.getValue(TenantSentMsg::class.java)
//                        if(tenant!!.getTenatId().equals(firebaseUser!!.uid)){
//                            tenantSentMsgList!!.add(tenant)
//                        }
//                    }
//                    tenantSndMsgAdapter!!.notifyDataSetChanged()
//                }
//            }
//        })
//    }
//
//
//    private fun homeLordSentsAllMessage() {
//        var hlSMRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")
//        hlSMRef.addValueEventListener( object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for (item in snapshot.children){
//                        var hlSM = item.getValue(HomeLordSent::class.java)
//                        if(hlSM!!.getHomeLordId().equals(firebaseUser!!.uid)){
//                            homlordSentMsgList!!.add(hlSM)
//                        }
//                    }
//                    hlSentAdapter!!.notifyDataSetChanged()
//                }
//            }
//        })
//    }
//
//    private fun checkUserCurrentStatus() {
//        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
//        userRef.addValueEventListener( object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    var user = snapshot.getValue(User::class.java)
//                    if(user!!.getUser() == "Homelord"){
//                        currentUserStatus = "Homelord"
//                    }else{
//                        currentUserStatus = "Tenant"
//                    }
//                }
//            }
//        })
//    }
//
//
//    private fun retrieveAllHLMsgList() {
//        var bulidingRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")
//        bulidingRef.addValueEventListener( object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for(item in snapshot.children){
//                    var message = item.getValue(HomeLordSent::class.java)
//                        homlordSentMsgList?.add(message!!)
//                    }
//                    hlSentAdapter!!.notifyDataSetChanged()
//                }
//            }
//        })
//    }


}