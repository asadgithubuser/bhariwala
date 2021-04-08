package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.Models.User
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomelordReceivedRentBillAdapter (private val mContext: Context, private val mPayRentList: List<PayRent>, tenanttName: String)
    : RecyclerView.Adapter<HomelordReceivedRentBillAdapter.ViewHolder>(){

    var tenantName = tenanttName

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomelordReceivedRentBillAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.homelord_received_rent_bill_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPayRentList.size
    }

    override fun onBindViewHolder(holder: HomelordReceivedRentBillAdapter.ViewHolder, position: Int) {
        var payRen = mPayRentList[position]


        holder.payRent_tntname.text = tenantName
        holder.payRent_fname.text = "Flat: "+payRen.getFlatName()
        holder.payRent_bname.text = "Building: "+payRen.getBuildingName()

        holder.payRent_time.text = payRen.getTime()
        holder.payRent_date.text = payRen.getDate()
        holder.payRent_message.text = "You have Received from tenant ${tenantName} only ${payRen.getPaidRentAmount()} " +
                "tk for monthly bill at ${payRen.getPaidRentMonth()}"
        holder.payRent_rantAmount.text = payRen.getPaidRentAmount()+" tk"
        holder.payRent_rentMOnth.text = payRen.getPaidRentMonth()
        holder.payRent_via_service.text = payRen.getPayViaService()

        if(payRen.getIsPending() == "0"){
            holder.bill_receive_btn.text = "Receive"
        }else{
            holder.bill_receive_btn.text = "Received"
        }

        holder.bill_receive_btn.setOnClickListener {
            updateBillPendingStatus(payRen.getPayId())
        }

    }

    private fun updateBillPendingStatus(payId: String) {
        var payRef = FirebaseDatabase.getInstance().reference.child("PayRents")
        var payMap = HashMap<String, Any>()
        
        payMap["isPending"] = "1"
        
        payRef.child(payId).updateChildren(payMap).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(mContext, "You have confirm to press the receive button.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(mContext, "Error: "+task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var payRent_tntname: TextView = itemView.findViewById(R.id.receivedRentBill_tenant_name)
        var payRent_time: TextView = itemView.findViewById(R.id.receivedRentBill_time)
        var payRent_date: TextView = itemView.findViewById(R.id.receivedRentBill_date)
        var payRent_bname: TextView = itemView.findViewById(R.id.receivedRentBill_building)
        var payRent_fname: TextView = itemView.findViewById(R.id.receivedRentBill_flat_name)
        var payRent_message: TextView = itemView.findViewById(R.id.receivedRentBill_message)
        var payRent_rantAmount: TextView = itemView.findViewById(R.id.receivedRentBill_rentAmount)
        var payRent_rentMOnth: TextView = itemView.findViewById(R.id.receivedRentBill_rent_month)
        var payRent_via_service: TextView = itemView.findViewById(R.id.receivedRentBill_via_service)
        var bill_receive_btn: TextView = itemView.findViewById(R.id.homelord_receiveBill_btn)

    }




}