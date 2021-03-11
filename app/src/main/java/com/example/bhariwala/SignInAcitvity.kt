package com.example.bhariwala

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TimeUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInAcitvity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        go_login_to_register_btn.setOnClickListener {
            var intent = Intent(this, SignUPActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener {
            if(FirebaseAuth.getInstance().currentUser != null){
                loginUserMethod()
            }else{
                Toast.makeText(this, "User NOT Found!!. Please register first.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUserMethod(){
        var email = email.text.toString()
        var password = password.text.toString()
        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is Required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is Required", Toast.LENGTH_LONG).show()
            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Sign In")
                progressDialog.setMessage("please wait for sometimes, sign in processing")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var mAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        progressDialog.dismiss()
                        Toast.makeText(this, "You are logged in successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        progressDialog.dismiss()
                        var eMessage = task.exception
                        Toast.makeText(this, "SignIn Error: $eMessage", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}