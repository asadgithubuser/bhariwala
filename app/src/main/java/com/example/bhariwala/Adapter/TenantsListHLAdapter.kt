package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.R

class TenantsListHLAdapter(private val mContext: Context, private val mTenantList: List<Tenant>)
    : RecyclerView.Adapter<TenantsListHLAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TenantsListHLAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenantList.size
    }

    override fun onBindViewHolder(holder: TenantsListHLAdapter.ViewHolder, position: Int) {
        var tenant = mTenantList[position]
        holder.tenant_name.text = tenant.getTenantUserName()
        holder.flat_name.text = tenant.getFlatName()
        holder.building_name.text = tenant.getPropertyName()
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var tenant_name:TextView = itemView.findViewById(R.id.tHL_tenant_name)
        var flat_name:TextView = itemView.findViewById(R.id.tHL_flat_name)
        var building_name:TextView = itemView.findViewById(R.id.tHL_building_name)
    }

}