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
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Serviceman
import com.example.bhariwala.Models.Tenant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.flat_item.view.*
import kotlinx.android.synthetic.main.serviceman_item.view.*

class ServicemanAdapter (private var mContext: Context, private var servicemanList: List<Serviceman>)
    : RecyclerView.Adapter<ServicemanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicemanAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.serviceman_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return servicemanList.size
    }

    override fun onBindViewHolder(holder: ServicemanAdapter.ViewHolder, position: Int) {
        var serviceman = servicemanList[position]

        holder.servc_name_text.text = serviceman.getServicemnName()
        holder.servc_phone_text.text = serviceman.getServicemnPhone()
        holder.servc_joindate_text.text = serviceman.getServicemnJoinDate()
        holder.servc_duty_text.text = serviceman.getSelectedServicemnDuty()
        holder.servc_type_text.text = serviceman.getSelectedSrvcmnType()


        holder.servicman_setails_btn.setOnClickListener {
            var intent = Intent(mContext, ServicemanDetailsActivity::class.java)
            intent.putExtra("servicemanId", serviceman.getServicemanId())
            mContext.startActivity(intent)
        }


    }
    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var servc_name_text : TextView = itemView.servc_name_text
        var servc_phone_text : TextView = itemView.servc_phone_text
        var servc_joindate_text : TextView = itemView.servc_joindate_text
        var servc_duty_text : TextView = itemView.servc_duty_text
        var servc_type_text : TextView = itemView.servc_type_text
        var servicman_setails_btn : AppCompatButton = itemView.servicman_setails_btn
    }





}