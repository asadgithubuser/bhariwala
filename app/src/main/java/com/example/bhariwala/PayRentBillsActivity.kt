package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.HomelordReceivedRentBillAdapter
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.Models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayRentBillsActivity : AppCompatActivity() {

    private var payRentBillList: MutableList<PayRent>? = null
    private var payRentBillAdapter: HomelordReceivedRentBillAdapter? = null
    private var tenantID: String? = null
    private var tenantName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_rent_bills)


        // values came from HomeLordBillsListAdapter
        tenantID = intent?.getStringExtra("tenantID")

        getTenantIdByTenantName(tenantID!!)


        var recyclerView = findViewById<RecyclerView>(R.id.payRentBills_recycerview)
        recyclerView.setHasFixedSize(true)

        payRentBillList = ArrayList()
        payRentBillAdapter = HomelordReceivedRentBillAdapter(this, payRentBillList as MutableList<PayRent>)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = payRentBillAdapter


    }

    private fun getTenantIdByTenantName(tenantID : String) {
        var tuserRef = FirebaseDatabase.getInstance().reference.child("Users").child(tenantID)
        tuserRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val usert = snapshot.getValue(User::class.java)
                    if(usert!!.getUid().equals(tenantID)){
                        getPayRentinTenantName(usert.getUid())
                    }

                }
            }
        })
    }

    private fun getPayRentinTenantName(tenantUserId: String) {
        var pRentRef = FirebaseDatabase.getInstance().reference.child("PayRents").child(tenantUserId)
        pRentRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    payRentBillList!!.clear()
                    for(item in snapshot.children){
                        val pRent = item.getValue(PayRent::class.java)
                        //if(pRent!!.getTenantId().equals(tenantUserId)){
                            payRentBillList!!.add(pRent!!)
                        //}
                    }
                    payRentBillAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }



}