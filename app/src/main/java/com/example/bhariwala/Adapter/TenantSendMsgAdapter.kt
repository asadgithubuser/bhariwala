package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.TenantSentMsg
import com.example.bhariwala.R

class TenantSendMsgAdapter(private val mContext:Context, private val mTenantMsgList: List<TenantSentMsg>)
    :RecyclerView.Adapter<TenantSendMsgAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantSendMsgAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_sent_msg_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenantMsgList.size
    }

    override fun onBindViewHolder(holder: TenantSendMsgAdapter.ViewHolder, position: Int) {
        var tsm_Obj = mTenantMsgList[position]

        holder.tsm_receiver_homelord_name.text = tsm_Obj.getHomeLrdName()
        holder.tsm_sent_msg_time.text = tsm_Obj.getTime()
        holder.tsm_sent_msg_date.text = tsm_Obj.getDate()
        holder.tsm_sent_msg_text.text = tsm_Obj.getMsgText()
        holder.tsm_sent_msg_sbject.text = tsm_Obj.getMsgSubject()

    }


    inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView){
        var tsm_receiver_homelord_name : TextView = itemView.findViewById(R.id.tsm_receiver_homelord_name)
        var tsm_sent_msg_time : TextView = itemView.findViewById(R.id.tsm_sent_msg_time)
        var tsm_sent_msg_date : TextView = itemView.findViewById(R.id.tsm_sent_msg_date)
        var tsm_sent_msg_text : TextView = itemView.findViewById(R.id.tsm_sent_msg_text)
        var tsm_sent_msg_sbject : TextView = itemView.findViewById(R.id.tsm_sent_msg_sbject)
    }


}