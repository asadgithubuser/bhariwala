package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bhariwala.Adapter.MessagesPageAdapter
import com.example.bhariwala.Fragments.ReceivedMessageFragment
import com.example.bhariwala.Fragments.SentMessageFragment
import com.example.bhariwala.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_message_main.*

class MessageMainAcitivity : AppCompatActivity() {
    //private var userCurrentStatus: String? = null

    var userStatus = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_main)
        setSupportActionBar(message_toolbar)
        var actinnBar = getSupportActionBar()
        if(actinnBar != null){
            actinnBar.setTitle("Message")
            actinnBar.setHomeAsUpIndicator(R.drawable.back_icon)
            actinnBar.setDisplayHomeAsUpEnabled(true)
        }
        //var userCurrentStatus = intent.getStringExtra("userStatus")

        var myHomeLordId = intent.getStringExtra("myHomeLordId")

        var adapter = MessagesPageAdapter(supportFragmentManager)
        adapter.addFragment(SentMessageFragment(), "SENT MESSAGE")
        adapter.addFragment(ReceivedMessageFragment(), "RECEIVED MESSAGE")

        message_tabs_viewPager.adapter = adapter
        message_tab_layout.setupWithViewPager(message_tabs_viewPager)


        // ====== send message fab
        fab_send_msg_from_ama.setOnClickListener {
            if(userStatus == "Homelord"){
                startActivity(Intent(this, SendMessageActivity::class.java))
            }else if(userStatus == "Tenant"){
                var intent = Intent(this, TenantMsgSendActivity::class.java)
                intent.putExtra("myHomeLordId", myHomeLordId)
                startActivity(intent)
            }

        }

        checkUserCurrentStatus()
    }

//     fun SentMessageFragmentFethod(): Fragment {
//        var bundle = Bundle()
//        var fragment = SentMessageFragment()
//        bundle.putString("userStatuss", userStatus)
//        fragment.arguments = bundle
//
//        supportFragmentManager.beginTransaction().replace(R.id.homlord_sent_msg_frame, fragment).commit()
//
//        return fragment
//    }


    private fun checkUserCurrentStatus() {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    userStatus = user!!.getUser()
                }
            }
        })
    }


}