package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.*
import com.example.bhariwala.Models.Property
import kotlinx.android.synthetic.main.property_item.view.*

class PropertyAdapter (private var mContext: Context, private var mPropertyList: MutableList<Property>)
    : RecyclerView.Adapter<PropertyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.property_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPropertyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var property = mPropertyList[position]

        holder.buildingName.text = property.getBuildingName()
        holder.buildingAddress.text = property.getAddress()+", "+property.getSection()+", "+property.getCity()+", "+property.getDivision()




        holder.property_flats_btn.setOnClickListener {
            var intent = Intent(mContext, FlatActivity::class.java)
            intent.putExtra("propertyId", property.getPropertyId())
            intent.putExtra("homeLordId", property.getHomeLordId())
            mContext.startActivity(intent)
        }

        holder.property_rentmanagemtn_btn.setOnClickListener {
            var intent = Intent(mContext, RentMangementHomelordActivity::class.java)
            intent.putExtra("propertyId", property.getPropertyId())
            mContext.startActivity(intent)
        }

        holder.property_tenants_btn.setOnClickListener {
            var intent = Intent(mContext, HomeLordTenantsActivity::class.java)
            intent.putExtra("isListForProperty", "1")
            intent.putExtra("propertyId", property.getPropertyId())
            mContext.startActivity(intent)
        }

        holder.property_sendmessage_btn.setOnClickListener {
            mContext.startActivity(Intent(mContext, SendMessageActivity::class.java))
        }


    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var buildingName: TextView = itemView.pp_buildingName
        var buildingAddress: TextView = itemView.pp_address
        var property_flats_btn: AppCompatButton = itemView.property_flats_btn
        var property_rentmanagemtn_btn: AppCompatButton = itemView.property_rentmanagemtn_btn
        var property_tenants_btn: AppCompatButton = itemView.property_tenants_btn
        var property_sendmessage_btn: AppCompatButton = itemView.property_sendmessage_btn
    }
}