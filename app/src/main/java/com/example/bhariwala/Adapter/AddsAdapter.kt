package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.bhariwala.AdsDetailsActivity
import com.example.bhariwala.FlatAdsDetailsActivity
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Property
import com.example.bhariwala.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ads_details.*
import kotlinx.android.synthetic.main.flat_ads_item.view.*

class AddsAdapter(private var mContext: Context, private var mAdsList: MutableList<Ad>):RecyclerView.Adapter<AddsAdapter.ViewHolder>() {
    val imageList = ArrayList<SlideModel>()
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
        holder.flat_rent.text = ad.getRent()+" Tk"
        holder.t_rooms.text = ad.getTotalRooms()
        holder.t_baths.text = ad.getTotalBaths()
        holder.belcony.text = ad.getBelcony()
        holder.furmist.text = ad.getFurnistType()

        getPropertyIdBYFlatId(holder, ad.getFlatId())

        getAllFlatImageStorage(holder, ad.getFlatName())


        holder.itemView.setOnClickListener{
            var intent = Intent(mContext, AdsDetailsActivity::class.java)
            intent.putExtra("adId", ad.getAdId())
            mContext.startActivity(intent)
        }


    }

    private fun getAllFlatImageStorage(holder: ViewHolder, flatName: String) {
        var count = 0
        var storageRef = FirebaseStorage.getInstance().reference.child(flatName)
        storageRef.listAll().addOnSuccessListener( OnSuccessListener { result ->

            if(count == 0){
                result.items.forEach{storageRef ->
                    getAdsImagesForSlide(holder, flatName, storageRef.name)

                 count ++
                }
            }

        })
    }

    private fun getAdsImagesForSlide(holder: ViewHolder, flatName: String, fileName: String) {
        var flatRef = FirebaseStorage.getInstance().reference.child(flatName).child(fileName)
        flatRef.downloadUrl.addOnCompleteListener { task ->
            imageList.add(SlideModel(task.result.toString()))

            Picasso.get().load(task.result.toString()).into(holder.adsImgView)

//            holder.slideImgView(imageList)
//            //holder.slideImgView.setImageList(imageList, ScaleTypes.FIT)
//            holder.slideImgView.startSliding(4000)
        }
    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var flat_name : TextView = itemView.flatt_name
        var flat_rent : TextView = itemView.rentt
        var t_rooms : TextView = itemView.ttotal_rooms
        var t_baths : TextView = itemView.ttotal_baths
        var belcony : TextView = itemView.tt_belcony
        var furmist : TextView = itemView.isFurnistt
        var location : TextView = itemView.flatItem_location
        val adsImgView :ImageView = itemView.findViewById(R.id.ads_in_flatImg)
    }




    private fun getPropertyIdBYFlatId(holder:ViewHolder, flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getFlatId().equals(flatId)){
                            getFlatAddressFromProperty(holder, flatItem.getPropertyId())
                        }
                    }
                }
            }
        } )
    }


    private fun getFlatAddressFromProperty(holder: ViewHolder, propertyId: String) {
        var propertyRef = FirebaseDatabase.getInstance().getReference().child("Properties").child(propertyId)
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var propertyItem = snapshot.getValue(Property::class.java)

                    holder.location.text = propertyItem!!.getThana()
                }
            }
        } )
    }











}