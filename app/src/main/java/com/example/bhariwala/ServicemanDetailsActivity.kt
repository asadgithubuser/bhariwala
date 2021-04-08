package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Serviceman
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_serviceman_details.*
import kotlinx.android.synthetic.main.fragment_home_lord.view.*

class ServicemanDetailsActivity : AppCompatActivity() {

    var servicemanId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serviceman_details)

        servicemanId = intent?.getStringExtra("servicemanId")

        getServicemanDetails(servicemanId!!)

        add_servicman_for_duty.setOnClickListener {
            var intent = Intent(this, AssignServicemanToHomelordActivity::class.java)
            intent.putExtra("servicemanId", servicemanId)
            startActivity(intent)
        }

    }

    private fun getServicemanDetails(servicemanId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Servicemans").child(servicemanId)
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var sssrvc = snapshot.getValue(Serviceman::class.java)
                        servicman_details_tipe.text = sssrvc!!.getSelectedSrvcmnType()
                        servicman_details_sName.text = sssrvc!!.getServicemnName()
                        servicman_details_phone.text = sssrvc!!.getServicemnPhone()
                        servicman_details_joindate.text = sssrvc!!.getServicemnJoinDate()
                        servicman_details_duty.text = sssrvc!!.getSelectedServicemnDuty()
                        servicman_details_salary.text = sssrvc!!.getServicemnSalary()+" tk"
                        servicman_details_age.text = sssrvc!!.getServicemnAge()+" Yrs"
                        servicman_details_address.text = sssrvc!!.getServicemnFullAddress()
                }
            }
        })
    }


}