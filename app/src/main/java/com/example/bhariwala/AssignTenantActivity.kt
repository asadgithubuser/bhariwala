package com.example.bhariwala

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.text.TextUtils
import android.widget.*
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_assign_tenant.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AssignTenantActivity : AppCompatActivity() {

    var flatId :String? = null
    var tenantId :String? = null
    var todayDate :String? = null
    var tenantUserList: ArrayList<String>? = null
    var flaAdapter: ArrayAdapter<String>? = null
    var selectedTenant = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_tenant)

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.title = "Add Tenant To A Flat"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        var cInstnace = Calendar.getInstance()
        var year = cInstnace.get(Calendar.YEAR)
        var month = cInstnace.get(Calendar.MONTH)
        var day = cInstnace.get(Calendar.DAY_OF_MONTH)

        ass_tnt_rent_month.setOnClickListener{
            var dtPicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                ass_tnt_rent_month.setText(""+mDay+"/"+mMonth+"/"+mYear+"")
            }, year, month, day )
            dtPicker.show()
        }
        tenantUserList = ArrayList()
        flaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, tenantUserList!!)
        ass_tnt_select_tenant.setAdapter(flaAdapter)
        ass_tnt_select_tenant.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            selectedTenant = parent.getItemAtPosition(position).toString()

           // getAlsoTenantId(selectedTenant)
        }

        flatId = intent.getStringExtra("flatId")

       retrieveFlatDetails(flatId)
        retrieveTenantNames()

        ass_tnt_add_tenant_btn.setOnClickListener {
            saveAssignedTenant()
        }

    }

    private fun getAlsoTenantId(selectedTenant: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var tenantUser = item.getValue(Tenant::class.java)
                        if(tenantUser!!.getTenantUserName().equals(selectedTenant)){
                            tenantId = tenantUser.getTenantId()
                        }
                    }
                }
            }
        })
    }

    private fun saveAssignedTenant() {
        var flatName = ass_tnt_flat_name.text.toString()
        var propertyName = ass_tnt_building_name.text.toString()
        var rent = ass_tnt_rent.text.toString()
        var date = ass_tnt_rent_month.text.toString()

        when{
//            TextUtils.isEmpty(flatName.toString()) -> showToast("")
//            TextUtils.isEmpty(propertyName.toString()) -> showToast("")
//            TextUtils.isEmpty(rent.toString()) -> showToast("")
            TextUtils.isEmpty(date.toString()) -> showToast("Rent Date should not be null")
            TextUtils.isEmpty(selectedTenant) -> showToast("Please select a tenant")
            else -> {
                var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
                var tenantId = tenantRef.push().key
                var tMap = HashMap<String, Any>()
                tMap["tenantId"] = tenantId!!
                tMap["tenantUserName"] = selectedTenant
                tMap["homeLordId"] = FirebaseAuth.getInstance().currentUser!!.uid
                tMap["flatName"] = flatName
                tMap["propertyName"] = propertyName
                tMap["rent"] = rent
                tMap["date"] = date

                tenantRef.child(tenantId).setValue(tMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        flatBookedUpdate(flatId.toString())
                        showToast("Tenant Added successfully")
                        finish()
                    }else{
                        showToast("Error: "+task.exception.toString())
                    }
                }
            }
        }

    }

    private fun flatBookedUpdate(currentFlatId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(currentFlatId)

        var flatMap = HashMap<String, Any>()
        flatMap["isBooked"] = "1"
        flatRef.updateChildren(flatMap)

    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun retrieveTenantNames() {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Users").orderByChild("name")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    tenantUserList!!.clear()
                    for(item in snapshot.children){
                        var tenantUser = item.getValue(User::class.java)
                        if(tenantUser!!.getUser().equals("Tenant")){
                            tenantUserList!!.add(tenantUser!!.getName())
                        }
                    }
                    flaAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }

    private fun retrieveFlatDetails(flatId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId!!)
        flatRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)

                    ass_tnt_flat_name.text = flat!!.getFlatName()
                    ass_tnt_building_name.text = flat!!.getPropertyName()
                    ass_tnt_rent.text = flat!!.getRent()+"/= monthly"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}