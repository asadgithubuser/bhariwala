package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.R

class HomeLordBillsListAdapter(private val mContext: Context, private val mPayRentList: List<Tenant>)
    : RecyclerView.Adapter<HomeLordBillsListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeLordBillsListAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_list_for_bill_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPayRentList.size
    }

    override fun onBindViewHolder(holder: HomeLordBillsListAdapter.ViewHolder, position: Int) {
        var tenant = mPayRentList[position]


            holder.tenant_name.text = tenant.getTenantUserName()
            holder.flat_name.text = "Flat: "+tenant.getFlatName()
            holder.building_name.text = "Building: "+tenant.getPropertyName()



        holder.all_received_bills.setOnClickListener{
            var intent = Intent(mContext, HomeLordBillsListAdapter::class.java)
            intent.putExtra("tenantUserName", tenant!!.getTenantUserName())
            mContext.startActivity(intent)
        }

    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var tenant_name: TextView = itemView.findViewById(R.id.payRent_tenant_name)
        var flat_name: TextView = itemView.findViewById(R.id.payRent_flat_name)
        var building_name: TextView = itemView.findViewById(R.id.payRent_building_name)
        var all_received_bills: TextView = itemView.findViewById(R.id.payRent_show_all_bills_list_for_tenant)
    }

}