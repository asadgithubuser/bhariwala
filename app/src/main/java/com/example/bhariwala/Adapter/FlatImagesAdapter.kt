package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.R
import kotlinx.android.synthetic.main.flat_item.view.*

class FlatImagesAdapter(private var mContext: Context, private var flatImgList: List<String>)
    : RecyclerView.Adapter<FlatImagesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlatImagesAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.flat_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flatImgList.size
    }

    override fun onBindViewHolder(holder: FlatImagesAdapter.ViewHolder, position: Int) {
        var flat = flatImgList[position]

      //  holder.flat_name.text = flat!!.getFlatName()



    }



    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var flat_name : TextView = itemView.flat_flat_name
    }
}
