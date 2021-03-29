package com.example.bhariwala

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.bhariwala.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tenant_msg_send.*
import kotlinx.android.synthetic.main.fragment_tenant.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.HashMap

class TenantMsgSendActivity : AppCompatActivity() {

    private var myHomeLordId: String? = null

    var send_time = ""
    var send_date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_msg_send)

        myHomeLordId = intent.getStringExtra("myHomeLordId")
        getHomeLordNameById(myHomeLordId)

        tsm_tenantSentMessage_btn.setOnClickListener {
            sentMsgToHomelord()
        }

        var sdf_date = SimpleDateFormat("mm/DD/yyyy")
        send_date = sdf_date.format(Date())

        var current_time = LocalDateTime.now()
        send_time = current_time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))








    }


    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }
    private fun sentMsgToHomelord() {
        var homeLrdName = sm_myHomelordName.text.toString()
        var msgSubject = tsm_msg_subject.text.toString()
        var msgText = tsm_msg_text.text.toString()
        when{
            TextUtils.isEmpty(msgSubject) -> showToast("Message Subject is required")
            TextUtils.isEmpty(msgText) -> showToast("Please write something on message Body")

            else -> {

                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Sending Message")
                progressDialog.setMessage("please wait, we are sending message to selected flats.")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var tsmRef = FirebaseDatabase.getInstance().reference.child("TenantSendMsg")
                var tsmId = tsmRef.push().key
                var tsmMap = HashMap<String, Any>()
                    tsmMap["tsmId"] = tsmId!!
                    tsmMap["homeLrdId"] = myHomeLordId!!
                    tsmMap["tenatId"] = FirebaseAuth.getInstance().currentUser!!.uid
                    tsmMap["homeLrdName"] = homeLrdName
                    tsmMap["msgSubject"] = msgSubject
                    tsmMap["msgText"] = msgText
                    tsmMap["time"] = send_time
                    tsmMap["date"] = send_date

                tsmRef.child(tsmId).setValue(tsmMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        progressDialog.dismiss()
                        showToast("Your message has been sent successfully")
                        finish()
                    }else{
                        progressDialog.dismiss()
                        showToast("Error: "+task.exception.toString())
                    }
                }

            }
        }
    }

    private fun getHomeLordNameById(myHomeLordId: String?) {
        var myHomelordName = findViewById<AppCompatButton>(R.id.sm_myHomelordName)
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(myHomeLordId!!)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    myHomelordName.text = user!!.getName()
                }
            }
        })
    }

}