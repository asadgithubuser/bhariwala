package com.example.bhariwala

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Property
import com.example.bhariwala.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_send_message.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SendMessageActivity : AppCompatActivity() {
    private var propertyList: MutableList<String>? = null
    private var flatList: ArrayList<String>? = null
    private var firebaseUser: FirebaseUser? = null


    var selected_property_name = ""
    var selected_flat_name = ""
    var date = ""
    var time = ""

    var flatId = ""
    var propertyId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        flatList = ArrayList()
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
            selected_flat_name = ""
        }

        var slectProperty = findViewById<AutoCompleteTextView>(R.id.select_properties)
        propertyList = ArrayList()
        var proAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, propertyList!!)
        slectProperty.threshold = 1
        slectProperty.setAdapter(proAdapter)

        slectProperty.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            var selectedItem = parent.getItemAtPosition(position).toString()
            selected_property_name = selectedItem
           retrieveAllFlats(selected_property_name)
        }


        var selectFlat = findViewById<AutoCompleteTextView>(R.id.select_flat_names)
        var flatAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, flatList!!)
        selectFlat.threshold = 1
        selectFlat.setAdapter(flatAdapter)

        selectFlat.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var selectedItem = parent.getItemAtPosition(position).toString()
            selected_flat_name = selectedItem

            retrieveFlatId(selected_flat_name)
        }


        var sdtf = SimpleDateFormat("dd/MM/yyyy")
        date = sdtf.format(Date())

        var currentDateTime= LocalDateTime.now()
         time = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

        send_message_allFlat_or_SingleFlat.setOnClickListener {
            sendMessageTOFlat()
        }


       // showTaost(selected_property_name)
        retrieveAllBuildings()

    }


    private fun sendMessageTOFlat() {
      //  var propertyName = findViewById<AutoCompleteTextView>(R.id.select_properties)
       // var flatName = findViewById<AutoCompleteTextView>(R.id.select_flat_names)
        var txtMsg = txtMessage.text.toString()

        when{
            TextUtils.isEmpty(selected_property_name) -> showTaost("property name should not be null")
            TextUtils.isEmpty(txtMsg) -> showTaost("message field is required")

            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Sending Message")
                progressDialog.setMessage("please wait, we are sending message to selected flats.")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var messageRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")

                var messageId = messageRef.push().key
                var messageMap = HashMap<String, Any>()
                messageMap["msgId"] = messageId!!
                messageMap["homeLordId"] = FirebaseAuth.getInstance().currentUser.uid
                messageMap["propertyId"] = propertyId
                messageMap["flatId"] = flatId
                messageMap["message"] = txtMsg
                messageMap["date"] = date
                messageMap["time"] = time

                messageRef.child(messageId).setValue(messageMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        progressDialog.dismiss()
                        showTaost("Your message has been sent successfully")
                        finish()
                    }else{
                        progressDialog.dismiss()
                        showTaost("Error: "+task.exception.toString())
                    }
                }

            }
        }

    }

    private fun showTaost(s: String){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun retrieveAllBuildings() {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Properties")
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var property = item.getValue(Property::class.java)
                        if(property!!.getHomeLordId().equals(firebaseUser!!.uid)){
                            propertyList!!.add(property!!.getBuildingName())
                        }
                    }
                }
            }
        })
    }

    private fun retrieveFlatId(selected_flat: String) {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Flats")
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var flat = item.getValue(Flat::class.java)
                        if(flat!!.getFlatName().equals(selected_flat)){
                            flatId = flat.getFlatId()
                       }
                    }
                }
            }
        })
    }
    private fun retrieveAllFlats(selected_property: String) {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Properties")
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var property = item.getValue(Property::class.java)
                        if(property!!.getBuildingName().equals(selected_property)){
                           propertyId = property.getPropertyId()
                           retrieveAllFlatName(property!!.getPropertyId())
                       }
                    }
                }
            }
        })
    }


    private fun retrieveAllFlatName(propertyId: String) {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Flats")
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    flatList!!.clear()
                    for(item in snapshot.children){
                        var flat = item.getValue(Flat::class.java)
                        if(flat!!.getPropertyId().equals(propertyId) && flat.getIsBooked().equals("1") ){
                            flatList?.add(flat!!.getFlatName())
                        }

                    }
                }
            }
        })
    }


}