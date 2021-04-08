package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.CarPark
import com.example.bhariwala.R

class CarParkingAdapter(private val mContext:Context, private val carList: List<CarPark>)
    :RecyclerView.Adapter<CarParkingAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarParkingAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.parking_car_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onBindViewHolder(holder: CarParkingAdapter.ViewHolder, position: Int) {

    }


    inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView){

    }

}