package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.ReceivedBillsForPropertyAdapter
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.Models.Property
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_homelord_rent_management.*

class RentMangementHomelordActivity : AppCompatActivity() {
    var propertyId : String? = null
    var totalDueAmount = 0
    var totalPaid = 0
    var totalRentAmount = 0

    private var receiveBillList: MutableList<PayRent>? = null
    private var receivedBillsForPropertyAdapter: ReceivedBillsForPropertyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homelord_rent_management)

        var actionBar = getSupportActionBar()
        if( actionBar != null){
            actionBar.setTitle("Rent Management")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        propertyId = intent?.getStringExtra("propertyId")

        getAllRentFromAProperty(propertyId)

        rentM_bill_details_list_btn.setOnClickListener {
            var recycler = findViewById<RecyclerView>(R.id.homelord_receiveBillList_details_recyclerView)
            recycler.setHasFixedSize(true)
            recycler.layoutManager = LinearLayoutManager(this)
            receiveBillList = ArrayList()
            receivedBillsForPropertyAdapter = ReceivedBillsForPropertyAdapter(this, receiveBillList as MutableList<PayRent>)
            recycler.adapter = receivedBillsForPropertyAdapter

            getAllReceiveBillsDetailsList(propertyId)
        }


    }

    private fun getAllReceiveBillsDetailsList(propertyId: String?) {
        var propertyReff = FirebaseDatabase.getInstance().reference.child("Properties").child(propertyId!!)
        propertyReff.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var property = snapshot.getValue(Property::class.java)
                    if(property!!.getPropertyId().equals(propertyId)){
                        getRentPaidDetails(property.getBuildingName())
                    }
                }
            }
        })
    }

    private fun getRentPaidDetails(buildingName: String) {
        var propertyReff = FirebaseDatabase.getInstance().reference.child("PayRents")
        propertyReff.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    receiveBillList!!.clear()
                    for(payrent in snapshot.children){
                        var payrent = payrent.getValue(PayRent::class.java)
                        if(payrent!!.getBuildingName().equals(buildingName)){
                            receiveBillList!!.add(payrent)
                        }
                    }
                    receivedBillsForPropertyAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }

    private fun getAllRentFromAProperty(propertyId: String?) {
        var propertyReff = FirebaseDatabase.getInstance().reference.child("Properties").child(propertyId!!)
        propertyReff.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var property = snapshot.getValue(Property::class.java)

                    rentM_building_name.text = property!!.getBuildingName()

                    getFlatByPropertyIdd(property!!.getPropertyId())
                }
            }
        })
    }

    private fun getFlatByPropertyIdd(propertyId: String) {
        var freff = FirebaseDatabase.getInstance().reference.child("Flats")
        freff.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var flat = item.getValue(Flat::class.java)
                        if(flat!!.getPropertyId().equals(propertyId) && flat.getIsBooked().equals("1")){
                            var rent = flat.getRent().toInt()
                            totalRentAmount += rent

                            rentM_total_rent.text = totalRentAmount.toString()+" tk"

                            findTotalDeuAmount(totalRentAmount, flat.getFlatName())
                        }
                    }

                }
            }
        })
    }

    private fun findTotalDeuAmount(totalRentAmount: Int, flatName: String) {

        var freff = FirebaseDatabase.getInstance().reference.child("PayRents")

        freff.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var pRent = item.getValue(PayRent::class.java)
                        if(pRent!!.getFlatName().equals(flatName) && pRent.getIsPending().equals("0")){
                            var paidRent = pRent.getPaidRentAmount().toInt()
                            totalPaid += paidRent

                            totalDueAmount = totalRentAmount - totalPaid

                            rentM_total_due_rent.text = totalDueAmount.toString()+" tk"
                        }else{
                            rentM_total_due_rent.text = totalRentAmount.toString()+" tk"
                        }
                    }

                }
            }
        })
    }

}