package com.example.bhariwala.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.R
import com.squareup.picasso.Picasso

class UploadFlatImagesAdapter (private var mContext: Context, private var flatImgList: List<Bitmap>)
    : RecyclerView.Adapter<UploadFlatImagesAdapter.ViewHolder>() {

//    lateinit var layoutInflater: LayoutInflater
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view.equals(`object`)
//    }
//
//    override fun getCount(): Int {
//        return flatImgList.size
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//      //  return super.instantiateItem(container, position)
//
//       layoutInflater = LayoutInflater.from(mContext)
//        var view = layoutInflater.inflate(R.layout.upload_flat_img_item, container, false)
//        var imgView = view.findViewById<ImageView>(R.id.flat_imgView)
//        imgView.setImageResource(flatImgList.get(position))
//        container.addView(view, 0)
//        return view
//
//    }
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
//    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadFlatImagesAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.upload_flat_img_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flatImgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var flatImg = flatImgList[position]

        Log.d("iggge", flatImg.toString())

       // holder.flat_imgView.setImageResource(flatImg.toInt())
       // Picasso.get().load(flatImg).into(holder.flat_imgView)
        holder.flat_imgView.setImageBitmap(flatImg)


    }


    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        var flat_imgView: ImageView = itemView.findViewById(R.id.flat_imgView)
       // var flat_TextView: TextView = itemView.findViewById(R.id.flat_TextView)

    }


}