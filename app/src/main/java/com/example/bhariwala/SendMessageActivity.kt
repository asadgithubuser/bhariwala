package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SendMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        var actionBar = getSupportActionBar()
        if( actionBar != null){
            actionBar.setTitle("Send Message")
        }
    }
}