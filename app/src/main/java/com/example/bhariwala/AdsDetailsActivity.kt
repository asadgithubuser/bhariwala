package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Models.Ad
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ads_details.*

class AdsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_details)

        var intent = intent
        var adId = intent.getStringExtra("adId")

        retribeFlatData(adId)

    }

    private fun retribeFlatData(adId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Ads").child(adId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val ad = snapshot.getValue(Ad::class.java)

                    generator_service.text = ad!!.getGenerator()
                    lift_service.text = ad!!.getLift()
                    security_service.text = ad!!.getSecurity()
                    gas_service.text = ad!!.getGas()
                    flat_about_details.text = ad!!.getAdBoutHome()
                    flat_title.text = ad!!.getAdTitle()
                    ad_details_flatName.text = ad!!.getFlatName()
                    d_rent_month.text = ad!!.getRentForMonth()
                    d_total_room.text =  "Total rooms "+ad!!.getTotalRooms()
                    d_total_bath.text =  "Total baths"+ad!!.getTotalBaths()
                    d_attached_bath.text = "Attached bath"+ad!!.getAttachedBath()
                    d_eletricity_bill.text = "Electricity - "+ad!!.getElectricityBill()
                    d_water_bill.text = "Water - "+ad!!.getWaterBill()
                    d_gas_bill.text = "Gas - "+ad!!.getGasBill()
                    d_service_bill.text = "Maintanence - "+ad!!.getMaintanenceBill()
                }
            }
        })
    }
}