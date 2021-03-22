package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.HomeLordSent
import com.example.bhariwala.R

class HomeLordSentAdapter(private val mContext: Context, private val mHSMessages: List<HomeLordSent>)
    : RecyclerView.Adapter<HomeLordSentAdapter.ViewHolder>(){
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): HomeLordSentAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.homelord_sent_msg_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mHSMessages.size
    }

    override fun onBindViewHolder(holder: HomeLordSentAdapter.ViewHolder, position: Int) {
        var sentMag = mHSMessages[position]

        holder.hl_sent_receiver_name.setText(sentMag!!.getHomeLordId())
        holder.hl_sent_building_name.setText(sentMag!!.getPropertyName())
        holder.hl_sent_flat_name.setText(sentMag!!.getFlatName())
        holder.hl_sent_msg_time.setText(sentMag!!.getTime())
        holder.hl_sent_msg_date.setText(sentMag!!.getDate())
        holder.hl_sent_msg_text.setText(sentMag!!.getMessage())
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var hl_sent_receiver_name: TextView = itemView.findViewById(R.id.hl_sent_receiver_name)
        var hl_sent_building_name: TextView = itemView.findViewById(R.id.hl_sent_building_name)
        var hl_sent_flat_name: TextView = itemView.findViewById(R.id.hl_sent_flat_name)
        var hl_sent_msg_time: TextView = itemView.findViewById(R.id.hl_sent_msg_time)
        var hl_sent_msg_date: TextView = itemView.findViewById(R.id.hl_sent_msg_date)
        var hl_sent_msg_text: TextView = itemView.findViewById(R.id.hl_sent_msg_text)
    }
}