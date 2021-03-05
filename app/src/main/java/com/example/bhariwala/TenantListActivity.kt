package com.example.bhariwala

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tenant_list.*

class TenantListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_list)

        tenant_details_show_btn.setOnClickListener {
            var dialog = Dialog(this, android.R.style.Theme_DeviceDefault_DialogWhenLarge)
            dialog.setContentView(R.layout.tenant_single_details)
            dialog.show()
        }
    }
}