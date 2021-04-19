package com.example.bhariwala.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.*
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeLordSentAdapter(private val mContext: Context, private val mHSMessages: List<HomeLordSent>)
    : RecyclerView.Adapter<HomeLordSentAdapter.ViewHolder>(){

    private var firebaseUser: FirebaseUser? = null
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): HomeLordSentAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.homelord_sent_msg_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mHSMessages.size
    }

    override fun onBindViewHolder(holder: HomeLordSentAdapter.ViewHolder, position: Int) {
        var sentMag = mHSMessages[position]

        holder.hl_sent_msg_time.setText(sentMag.getTime())
        holder.hl_sent_msg_date.setText(sentMag.getDate())
        holder.hl_sent_msg_text.setText(sentMag.getMessage())

        firebaseUser = FirebaseAuth.getInstance().currentUser
        checkUserCurrentStatus(holder, sentMag!!.getFlatId())

    }

    private fun getFlatPropertyNames(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var hlsentmsg = item.getValue(HomeLordSent::class.java)
                        if(hlsentmsg!!.getFlatId().equals(flatId) && hlsentmsg.getHomeLordId().equals(firebaseUser!!.uid)){
                            getFlatById(holder, hlsentmsg.getFlatId())
                            getTenantByHomelordId(holder, hlsentmsg.getFlatId())
                        }
                    }
                }
            }
        })
    }

    private fun getFlatById(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)

                    holder.hl_sent_flat_name.setText("Flat: "+flat!!.getFlatName())
                    holder.hl_sent_building_name.setText("Building: "+flat.getPropertyName())


                }
            }
        })
    }

    private fun getTenantByHomelordId(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var hlsentmsg = item.getValue(Tenant::class.java)
                        if(hlsentmsg!!.getFlatId().equals(flatId)){
                            getTenantFromUser(holder, hlsentmsg.getTenantId())
                        }
                    }
                }
            }
        })
    }
    private fun getTenantFromUser(holder: ViewHolder, userId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                        holder.hl_sent_receiver_name.setText(user!!.getName())
                        holder.hl_sent_userStatus.setText(user!!.getUser())
                }
            }
        })
    }


    // ========== tennant operation


    private fun getFlatPropertyNamesTenant(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var hlsentmsg = item.getValue(HomeLordSent::class.java)
                        if(hlsentmsg!!.getFlatId().equals(flatId)){
                            getFlatByIdTenant(holder, hlsentmsg.getFlatId())
                            getFlatPrfdsfsaTenant(holder, hlsentmsg.getFlatId())
                        }
                    }
                }
            }
        })
    }

    private fun getFlatByIdTenant(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)

                    holder.hl_sent_flat_name.setText("Flat: "+flat!!.getFlatName())
                    holder.hl_sent_building_name.setText("Building: "+flat.getPropertyName())


                }
            }
        })
    }

    private fun getFlatPrfdsfsaTenant(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var hlsentmsg = item.getValue(Tenant::class.java)
                        if(hlsentmsg!!.getFlatId().equals(flatId) && hlsentmsg.getTenantId().equals(firebaseUser!!.uid)){
                            getUserFromTenantTenant(holder, hlsentmsg.getHomeLordId())
                        }
                    }
                }
            }
        })
    }
    private fun getUserFromTenantTenant(holder: ViewHolder, userId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    holder.hl_sent_receiver_name.setText(user!!.getName())
                    holder.hl_sent_userStatus.setText(user!!.getUser())
                }
            }
        })
    }




    private fun checkUserCurrentStatus(holder: ViewHolder, flatId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    if(user!!.getUser().equals("Homelord")){
                        getFlatPropertyNames(holder, flatId)
                    }else if(user!!.getUser().equals("Tenant")){
                        getFlatPropertyNamesTenant(holder, flatId)
                    }
                }
            }
        })
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var hl_sent_receiver_name: TextView = itemView.findViewById(R.id.hl_sent_receiver_name)
        var hl_sent_building_name: TextView = itemView.findViewById(R.id.hl_sent_building_name)
        var hl_sent_flat_name: TextView = itemView.findViewById(R.id.hl_sent_flat_name)
        var hl_sent_msg_time: TextView = itemView.findViewById(R.id.hl_sent_msg_time)
        var hl_sent_msg_date: TextView = itemView.findViewById(R.id.hl_sent_msg_date)
        var hl_sent_msg_text: TextView = itemView.findViewById(R.id.hl_sent_msg_text)
        var hl_sent_userStatus: TextView = itemView.findViewById(R.id.hl_sent_userStatus)
    }
}