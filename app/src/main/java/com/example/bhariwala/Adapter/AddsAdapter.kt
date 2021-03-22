package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.AdsDetailsActivity
import com.example.bhariwala.FlatAdsDetailsActivity
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.R
import kotlinx.android.synthetic.main.flat_ads_item.view.*

class AddsAdapter(private var mContext: Context, private var mAdsList: MutableList<Ad>):RecyclerView.Adapter<AddsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddsAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.flat_ads_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mAdsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ad = mAdsList[position]

        holder.flat_name.text = ad.getAdTitle()
        holder.flat_rent.text = ad.getRent()
        holder.t_rooms.text = ad.getTotalRooms()
        holder.t_baths.text = ad.getTotalBaths()
        holder.belcony.text = ad.getBelcony()
        holder.furmist.text = ad.getFurnistType()

        holder.itemView.setOnClickListener{
            var intent = Intent(mContext, AdsDetailsActivity::class.java)
            intent.putExtra("adId", ad.getAdId())
            mContext.startActivity(intent)
        }


    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var flat_name : TextView = itemView.flatt_name
        var flat_rent : TextView = itemView.rentt
        var t_rooms : TextView = itemView.ttotal_rooms
        var t_baths : TextView = itemView.ttotal_baths
        var belcony : TextView = itemView.tt_belcony
        var furmist : TextView = itemView.isFurnistt
    }
}