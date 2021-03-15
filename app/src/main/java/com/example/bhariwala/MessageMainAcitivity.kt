package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Adapter.MessagesPageAdapter
import com.example.bhariwala.Fragments.ReceivedMessageFragment
import com.example.bhariwala.Fragments.SentMessageFragment
import kotlinx.android.synthetic.main.activity_message_main.*

class MessageMainAcitivity : AppCompatActivity() {
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


        var adapter = MessagesPageAdapter(supportFragmentManager)
        adapter.addFragment(SentMessageFragment(), "SENT MESSAGE")
        adapter.addFragment(ReceivedMessageFragment(), "RECEIVED MESSAGE")

        message_tabs_viewPager.adapter = adapter
        message_tab_layout.setupWithViewPager(message_tabs_viewPager)

        // ====== send message fab
        fab_send_msg_from_ama.setOnClickListener {
            startActivity(Intent(this, SendMessageActivity::class.java))
        }
    }
}