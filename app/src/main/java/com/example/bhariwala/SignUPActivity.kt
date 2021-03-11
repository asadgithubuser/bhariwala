package com.example.bhariwala

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUPActivity : AppCompatActivity() {

    private var user = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var userTypes = arrayOf("Homelord", "Tenant")

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, userTypes)
        signUp_select_type.setAdapter(adapter)
        signUp_select_type.threshold = 1

        signUp_select_type.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            user = selectedItem
           // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        // Set a dismiss listener for auto complete text view
//        signUp_select_type.setOnDismissListener {
//            Toast.makeText(applicationContext,"Suggestion closed.",Toast.LENGTH_SHORT).show()
//        }


        // Set a click listener for root layout
//        signUp_select_type.setOnClickListener{
//            val text = signUp_select_type.text
//            Toast.makeText(applicationContext,"Inputted : $text",Toast.LENGTH_SHORT).show()
//        }


        // Set a focus change listener for auto complete text view
//        signUp_select_type.onFocusChangeListener = View.OnFocusChangeListener{
//                view, b ->
//            if(b){
//                // Display the suggestion dropdown on focus
//                signUp_select_type.showDropDown()
//            }
//        }


        ///========== sign up operation =============
        register_btn.setOnClickListener {
            var fullname = fullName.text.toString()
            var profession = profession.text.toString()
            var email = email.text.toString()
            var password = password.text.toString()
            when{
                TextUtils.isEmpty(user) -> Toast.makeText(this, "User Type should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(fullname) -> Toast.makeText(this, "Full Name should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(profession) -> Toast.makeText(this, "Your profession should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(email) -> Toast.makeText(this, "Email should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(password) -> Toast.makeText(this, "Password should not be null", Toast.LENGTH_LONG).show()
                else ->{
                    var progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Sign Up")
                    progressDialog.setMessage("please wait, while may we take few seconds.")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            saveRegisterDetails(fullname, profession, email, password, progressDialog)
                        }else{
                            var errMessage = task.exception.toString()
                            Toast.makeText(this, "SignUp Error: $errMessage", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                     }
                }
            }
        }
    }

    private fun saveRegisterDetails(fullname: String, profession: String, email: String, password: String, progressDialog: ProgressDialog) {
        var currentUid = FirebaseAuth.getInstance().currentUser!!.uid
        var userRef = FirebaseDatabase.getInstance().reference.child("Users")
        var userMap = HashMap<String, Any>()

        userMap["uid"] = currentUid
        userMap["name"] = fullname
        userMap["user"] = user
        userMap["profession"] = profession
        userMap["email"] = email
        userMap["password"] = password
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/bhariwala-f7aa6.appspot.com/o/userImage%2Fprofile.png?alt=media&token=791f691b-3b40-4ba0-9bbd-199bed29f218"

        userRef.child(currentUid).setValue(userMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this, "Account has been created successfully", Toast.LENGTH_LONG).show()

                val intent = Intent(this, SignInAcitvity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()


            }else{
                progressDialog.dismiss()
                Toast.makeText(this, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }

    }

}