package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tenant.*

class TenantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant)

        homeLord_btn_from_tenant.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        tenant_notice.setOnClickListener {

        }
        tenant_payBill.setOnClickListener {
            startActivity(Intent(this, BillsActivity::class.java))
        }
        tenant_allads.setOnClickListener {
            startActivity(Intent(this, FlatAddsActivity::class.java))
        }
        tenant_payrent.setOnClickListener {
            startActivity(Intent(this, PayRentActivity::class.java))
        }
    }
}