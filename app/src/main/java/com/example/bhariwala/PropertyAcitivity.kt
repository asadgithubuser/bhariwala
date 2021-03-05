package com.example.bhariwala

import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_tenant.*

class PropertyAcitivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)


        //setSupportActionBar(property_toolbar_id)
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Property List")
        }

        property_flat_btn.setOnClickListener {
            startActivity(Intent(this, FlatActivity::class.java))
        }

        property_rentManagement_btn.setOnClickListener {
            startActivity(Intent(this, RentManagementActivity::class.java))
        }

        property_tenant_btn.setOnClickListener {
            startActivity(Intent(this, TenantListActivity::class.java))
        }

        send_message_from_property_item.setOnClickListener {
            startActivity(Intent(this, SendMessageActivity::class.java))
        }

        add_property_fab_btn2.setOnClickListener {
            startActivity(Intent(this, AddPropertyActivity::class.java))
        }
        // ======== show xml layout file without activity file

    }

    private fun showSendMessagePopUp() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Send Message")
        var inflater = layoutInflater.inflate(R.layout.send_message_popup, null)
        dialog.setView(inflater)
        dialog.show()
    }

}