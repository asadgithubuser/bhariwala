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
import com.example.bhariwala.FlatDetailsActivity
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.R
import kotlinx.android.synthetic.main.flat_item.view.*

class FlatAdapter(private var mContext: Context, private var flatList: List<Flat>): RecyclerView.Adapter<FlatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlatAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.flat_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flatList.size
    }

    override fun onBindViewHolder(holder: FlatAdapter.ViewHolder, position: Int) {
        var flat = flatList[position]
        holder.flat_name.text = flat!!.getFlatName()
        holder.building_name.text = flat!!.getPropertyName()

        holder.flat_details.setOnClickListener {
            var intent = Intent(mContext, FlatDetailsActivity::class.java)
            intent.putExtra("flatId", flat!!.getFlatId())
            mContext.startActivity(intent)
        }

    }


    inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView) {
        var flat_name : TextView = itemView.flat_flat_name
        var building_name : TextView = itemView.flat_building_name
        var flat_details :AppCompatButton = itemView.flat_details_show_btn
    }
}