package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_flat_image_slider.*

class FlatImageSliderAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_image_slider)

        var actionBr = getSupportActionBar()
        if(actionBr != null){
            actionBr.setDisplayHomeAsUpEnabled(true)
        }

        var imgList = intent.getStringArrayListExtra("flatImageList")
        var imgListPosition = intent.getIntExtra("imageListPosition", 0)


        Picasso.get().load(imgList?.get(imgListPosition)).into(flat_single_img)


    }
}