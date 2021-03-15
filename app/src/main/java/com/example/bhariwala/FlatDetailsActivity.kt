package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Models.Flat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_flat_details.*

class FlatDetailsActivity : AppCompatActivity() {
    private var flatId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_details)

        flatId = intent.getStringExtra("flatId")

        retribeFlatData(flatId)
    }

    private fun retribeFlatData(flatId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)

                    flat_name_in_details.text = flat!!.getFlatName()
                    d_rent_month.text = flat!!.getRentForMonth()
                    d_total_room.text =  "Total rooms "+flat!!.getTotalRooms()
                    d_total_bath.text =  "Total baths "+flat!!.getTotalBaths()
                    d_attached_bath.text = "Attached bath "+flat!!.getAttachedBath()
                    d_eletricity_bill.text = "Electricity - "+flat!!.getElectricityBill()
                    d_water_bill.text = "Water - "+flat!!.getWaterBill()
                    d_gas_bill.text = "Gas - "+flat!!.getGasBill()
                    d_service_bill.text = "Maintanence - "+flat!!.getMaintanenceBill()

                }
            }
        })
    }

}