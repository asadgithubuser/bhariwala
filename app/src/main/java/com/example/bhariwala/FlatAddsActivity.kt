package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_flat_adds.*

class FlatAddsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_adds)

        tenant_flat_ads_item_wraper.setOnClickListener {
            var intent = Intent(this, FlatAdsDetailsActivity::class.java)
            startActivity(intent)
        }

    }
}