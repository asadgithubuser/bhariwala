package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReceivedBillsForPropertyAdapter (private val mContext: Context, private val mPayRentList: List<PayRent>)
    : RecyclerView.Adapter<ReceivedBillsForPropertyAdapter.ViewHolder>(){

    var tenantName = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReceivedBillsForPropertyAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.received_bill_list_details_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPayRentList.size
    }

    override fun onBindViewHolder(holder: ReceivedBillsForPropertyAdapter.ViewHolder, position: Int) {
        var payRen = mPayRentList[position]

        holder.payRent_hlname.text = tenantName
        holder.payRent_fname.text = "Flat: "+payRen.getFlatName()
        holder.payRent_bname.text = "Building: "+payRen.getBuildingName()

        holder.payRent_time.text = payRen.getTime()
        holder.payRent_date.text = payRen.getDate()
        holder.payRent_rantAmount.text = payRen.getPaidRentAmount()+" tk"
        holder.payRent_rentMOnth.text = payRen.getPaidRentMonth()
        holder.service_via.text = payRen.getPayViaService()


        getTenantName(payRen.getTenantId())
    }

    private fun getTenantName(tenantId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Tenants").child(tenantId)
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var tenantItem = snapshot.getValue(Tenant::class.java)
                    tenantName = tenantItem!!.getTenantUserName()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var payRent_hlname: TextView = itemView.findViewById(R.id.details_paidrent_hl_name)
        var payRent_time: TextView = itemView.findViewById(R.id.details_paidrent_time)
        var payRent_date: TextView = itemView.findViewById(R.id.details_paidrent_date)
        var payRent_bname: TextView = itemView.findViewById(R.id.details_paidrent_building)
        var payRent_fname: TextView = itemView.findViewById(R.id.details_paidrent_flat_name)
        var payRent_rantAmount: TextView = itemView.findViewById(R.id.details_paidrent_rentAmount)
        var payRent_rentMOnth: TextView = itemView.findViewById(R.id.details_paidrent_rent_month)
        var service_via: TextView = itemView.findViewById(R.id.details_paidrent_via_service)

    }




}