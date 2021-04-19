package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Models.Ad
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_flat_ads_details.*

class FlatAdsDetailsActivity : AppCompatActivity() {
    private var flatId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_ads_details)

        var intent = intent
        var flatId = intent.getStringExtra("flatId")


        retribeFlatData(flatId)
    }

    private fun retribeFlatData(flatId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Ads")
            flatRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(item in snapshot.children){
                            var ads = item.getValue(Ad::class.java)
                            if(ads!!.getFlatId().equals(flatId)){
                                fad_flat_title.text = ads!!.getAdTitle()
                                fad_home_type.text = ads!!.getHomeType()+" Vara"
                                fad_rent_available_for_month.text = ads!!.getRentForMonth()
                                fad_total_rms.text = "Total Rooms: "+ads!!.getTotalRooms()
                                fad_total_baths.text = "Total Baths: "+ads!!.getTotalBaths()
                                fad_attached_bath.text = "Attached Bath: "+ads!!.getAttachedBath()
                                fad_electricity_bill.text = "Electricity: "+ads!!.getElectricityBill()
                                fad_water_bill.text = "Water: "+ads!!.getWaterBill()
                                fad_gas_bill.text = "Gas: "+ads!!.getGasBill()
                                fad_service_charge_bill.text = "Maitanence: "+ads!!.getMaintanenceBill()
                                fad_about_home.text = ads!!.getAdBoutHome()
                                fad_genarator_service.text = ads!!.getGenerator()
                                fad_Lift_service.text = ads!!.getLift()
                                fad_security_service.text = ads!!.getSecurity()
                                fad_gas_service.text = ads!!.getGas()
                            }
                        }
                    }
                }
            })
    }
}