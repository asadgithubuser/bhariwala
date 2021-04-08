package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.HomeLordBillsListAdapter
import com.example.bhariwala.Adapter.TenantPayBillsAdapter
import com.example.bhariwala.Models.PayRent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tenant_pay_bills.*

class TenantPayBillsMainAcitivity : AppCompatActivity() {
    private var currentUser : FirebaseUser? = null
    private var payRentList: MutableList<PayRent>? = null
    private var billsListAdapter: TenantPayBillsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_pay_bills)


        tenant_pay_bill_fab_btn.setOnClickListener {
            startActivity(Intent(this, TenantPayBillActivity::class.java))
        }

        currentUser = FirebaseAuth.getInstance().currentUser

        var tenantRecyclerView = findViewById<RecyclerView>(R.id.tenant_paid_Bills_recyclerView)
        tenantRecyclerView.setHasFixedSize(true)
        tenantRecyclerView.layoutManager = LinearLayoutManager(this)

        payRentList = ArrayList()
        billsListAdapter = TenantPayBillsAdapter(this, payRentList as MutableList<PayRent>)
        tenantRecyclerView.adapter = billsListAdapter

        retrieveTenantForHomeLords()
    }

    private fun retrieveTenantForHomeLords() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("PayRents")
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    payRentList!!.clear()
                    for(item in snapshot.children){
                        val prent = item.getValue(PayRent::class.java)
                        if(prent!!.getTenantId().equals(currentUser!!.uid)){
                            payRentList!!.add(prent!!)
                        }
                    }
                    billsListAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }


}