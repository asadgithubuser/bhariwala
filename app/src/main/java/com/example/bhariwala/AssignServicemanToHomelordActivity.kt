package com.example.bhariwala

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.bhariwala.Models.Serviceman
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_assign_serviceman_to_homelord.*

class AssignServicemanToHomelordActivity : AppCompatActivity() {

    private var firabaseUser : FirebaseUser? = null
    private var currentUid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_serviceman_to_homelord)

        firabaseUser = FirebaseAuth.getInstance().currentUser

        var actionBr = getSupportActionBar()
        if(actionBr != null){
            actionBr.setTitle("Assign serviceman to duty")
            actionBr.setDisplayHomeAsUpEnabled(true)
        }

        var servicmanId = intent?.getStringExtra("servicemanId")

        smca_add_serviceman_btn.setOnClickListener {
            createServicemantAccount()
        }

        getServicemanInfo(servicmanId)

    }

    private fun createServicemantAccount() {
        var user_email = smca_user_email.text.toString()
        var user_password = smca_user_password.text.toString()
        var user_confirm_password = smca_user_confirm_password.text.toString()

        when{
            TextUtils.isEmpty(user_email) -> showToast("serviceman email not be bull")
            TextUtils.isEmpty(user_password) -> showToast("serviceman password not be bull")
            TextUtils.isEmpty(user_confirm_password) -> showToast("serviceman confirm password not be bull")

            else ->{
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Create Account")
                progressDialog.setMessage("please wait, we are creating account for serviceman")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var userAuth: FirebaseAuth = FirebaseAuth.getInstance()
                userAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        saveRegisterDetails(user_email, user_password, progressDialog)
                    }else{
                        var errMessage = task.exception.toString()
                        Toast.makeText(this, "SignUp Error: $errMessage", Toast.LENGTH_LONG).show()
                        //userAuth.signOut()
                        progressDialog.dismiss()
                    }
                }
            }
        }


    }
    private fun saveRegisterDetails(email: String, password: String, progressDialog: ProgressDialog) {
        var userName = addsfd_name.text.toString()
        var userType = addsfd_servicman_type.text.toString()

        var currentUidd = FirebaseAuth.getInstance().currentUser!!.uid
        var userRef = FirebaseDatabase.getInstance().reference.child("Users")
        var userMap = HashMap<String, Any>()

        userMap["uid"] = currentUid!!
        userMap["name"] = userName
        userMap["user"] = userType
        userMap["profession"] = ""
        userMap["email"] = email
        userMap["password"] = password
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/bhariwala-f7aa6.appspot.com/o/userImage%2Fprofile.png?alt=media&token=791f691b-3b40-4ba0-9bbd-199bed29f218"

        userRef.child(currentUidd).setValue(userMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this, "Account has been created successfully", Toast.LENGTH_LONG).show()

                finish()

            }else{
                progressDialog.dismiss()
                Toast.makeText(this, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                //FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }

    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun getServicemanInfo(servicmanId: String?) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Servicemans").child(servicmanId!!)
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val serviceman = snapshot.getValue(Serviceman::class.java)
                        addsfd_servicman_type.text = serviceman!!.getSelectedSrvcmnType()
                        addsfd_name.text = serviceman!!.getServicemnName()
                        addsfd_phone.text = serviceman!!.getServicemnPhone()
                        addsfd_joinDate.text = serviceman!!.getServicemnJoinDate()
                        addsfd_duty_time.text = serviceman!!.getSelectedServicemnDuty()

                    currentUid = serviceman.getServicemanId()
                }
            }
        })
    }










}