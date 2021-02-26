package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        go_login_to_register_btn?.setOnClickListener {
            Log.d("tag", "login btn click")
            var intent = Intent(this, SignUPActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}