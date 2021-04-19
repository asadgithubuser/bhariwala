package com.example.bhariwala.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.TenantSentMsg
import com.example.bhariwala.Models.User
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TenantSendMsgAdapter(private val mContext:Context, private val mTenantMsgList: List<TenantSentMsg>)
    :RecyclerView.Adapter<TenantSendMsgAdapter.ViewHolder>(){

    private var firebaseUser: FirebaseUser? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantSendMsgAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_sent_msg_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenantMsgList.size
    }

    override fun onBindViewHolder(holder: TenantSendMsgAdapter.ViewHolder, position: Int) {
        var tsm_Obj = mTenantMsgList[position]


        holder.tsm_sent_msg_time.text = tsm_Obj.getTime()
        holder.tsm_sent_msg_date.text = tsm_Obj.getDate()
        holder.tsm_sent_msg_text.text = tsm_Obj.getMsgText()
        holder.tsm_sent_msg_sbject.text = tsm_Obj.getMsgSubject()

        firebaseUser = FirebaseAuth.getInstance().currentUser
        checkUserCurrentStatus(holder, tsm_Obj.getHomeLrdId(), tsm_Obj.getTenatId())

    }

    ////// ======= homelord sent mesges
    private fun getUserFromTSMHomelord(holder: ViewHolder, homeLrdId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("TenantSendMsg")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tsm = item.getValue(TenantSentMsg::class.java)
                        if(tsm!!.getHomeLrdId().equals(homeLrdId)){
                            getUserByUserIdHomelord(holder, tsm.getTenatId())
                            Log.d("das", "two : "+tsm!!.getTenatId())
                        }
                    }
                }
            }
        })
    }

    private fun getUserByUserIdHomelord(holder: ViewHolder, userId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    //for(item in snapshot.children){
                        var user = snapshot.getValue(User::class.java)
                        if(user!!.getUid().equals(userId)){
                            Log.d("das", "two33 : "+user!!.getName())
                            holder.tsm_receiver_homelord_name.text = user!!.getName()+" test"
                            holder.tsm_receiver_userStatus.text = user.getUser()
                        }
                  //  }
                }
            }
        })
    }


    ////// ======= tenant sent mesges

    private fun getUserFromTSM(holder: ViewHolder, tenantId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("TenantSendMsg")
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (item in snapshot.children){
                        var tsm = item.getValue(TenantSentMsg::class.java)
                        if(tenantId.equals(firebaseUser!!.uid)){
                            getUserByUserId(holder, tsm!!.getHomeLrdId())
                        }
                    }
                }
            }
        })
    }

    private fun getUserByUserId(holder: ViewHolder, userId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    holder.tsm_receiver_homelord_name.text = user!!.getName()
                    holder.tsm_receiver_userStatus.text = user.getUser()
                }
            }
        })
    }


    private fun checkUserCurrentStatus(holder: ViewHolder, homeLrdId: String, tenantId: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    if(user!!.getUser() == "Homelord" && homeLrdId == firebaseUser!!.uid){
                        getUserFromTSMHomelord(holder, homeLrdId)
                        Log.d("das", "one : "+homeLrdId)
                    }else if(user!!.getUser().equals("Tenant")){
                        getUserFromTSM(holder, tenantId)
                    }
                }
            }
        })
    }



    inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView){
        var tsm_receiver_homelord_name : TextView = itemView.findViewById(R.id.tsm_receiver_homelord_name)
        var tsm_sent_msg_time : TextView = itemView.findViewById(R.id.tsm_sent_msg_time)
        var tsm_sent_msg_date : TextView = itemView.findViewById(R.id.tsm_sent_msg_date)
        var tsm_sent_msg_text : TextView = itemView.findViewById(R.id.tsm_sent_msg_text)
        var tsm_sent_msg_sbject : TextView = itemView.findViewById(R.id.tsm_sent_msg_sbject)
        var tsm_receiver_userStatus : TextView = itemView.findViewById(R.id.tsm_receiver_userStatus)
    }


}