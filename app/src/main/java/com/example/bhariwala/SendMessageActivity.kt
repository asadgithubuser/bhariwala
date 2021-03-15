package com.example.bhariwala

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_send_message.*

class SendMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        var actinBar = getSupportActionBar()
        if(actinBar != null){
            actinBar.setTitle("Send Message")
            actinBar.setDisplayHomeAsUpEnabled(true)
        }

        single_flat_select_btn.setOnClickListener {
            select_flat_to_send_msg.visibility = View.VISIBLE
        }

        all_flat_select_btn.setOnClickListener {
            select_flat_to_send_msg.visibility = View.GONE
        }




    }
}