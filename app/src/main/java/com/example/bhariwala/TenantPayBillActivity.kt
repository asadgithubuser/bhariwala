package com.example.bhariwala

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tenant_pay_bill.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class TenantPayBillActivity : AppCompatActivity() {
    var rentMonth = ""
    var selectedViaPay = ""
    var homeLordId22 : String? = null
    private var firebaseUser: FirebaseUser? = null

     var mDay = 0
    var mMonth = 0
    var mYear = 0

     var year = 0
    var month = 0
    var dayOfMonth = 0


     var yearNow = 0
    var monthNow = 0
    var dayNow = 0


    //var payViaList : ArrayList<String>? = null
    var payViaAdapter : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_pay_bill)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        var payViaList = arrayOf("bKash", "Nagat", "DBBL", "uCash")
        var seletViaPay = findViewById<AutoCompleteTextView>(R.id.addPay_bill_select_pay_via)
        seletViaPay.threshold = 1
        payViaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, payViaList)
        seletViaPay.setAdapter(payViaAdapter)
        seletViaPay.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            selectedViaPay = parent.getItemAtPosition(position).toString()
        }

        //======get current month
        var sdf = SimpleDateFormat("dd/MM/yyyy")


        var clnder = Calendar.getInstance()
        var year = clnder.get(Calendar.YEAR)
        var month = clnder.get(Calendar.MONTH)
        var day = clnder.get(Calendar.DAY_OF_MONTH)



        addPay_bill_select_pay_month.setOnClickListener {
//            var datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
//                    view, myear, mmonth, mdayOfMonth ->
//                addPay_bill_select_pay_month.setText(""+mdayOfMonth+"/"+mmonth+"/"+myear)
//            }, year, month, day)
//            datePicker.show()

                val mcurrentDate = Calendar.getInstance()
                val myFormat = "MMMM yyyy"
                val sdf = SimpleDateFormat(myFormat)
               // val monthDatePickerDialog: DatePickerDialog = object : DatePickerDialog(this,
                val monthDatePickerDialog = DatePickerDialog(this,
                        AlertDialog.THEME_HOLO_LIGHT, OnDateSetListener { view, year, month, dayOfMonth ->
                    mcurrentDate[Calendar.YEAR] = year
                    mcurrentDate[Calendar.MONTH] = month
                    addPay_bill_select_pay_month.setText(sdf.format(mcurrentDate.time))
                    mDay = dayOfMonth
                    mMonth = month
                    mYear = year
                }, mYear, mMonth, mDay)
//                {
//                    override fun onCreate(savedInstanceState: Bundle) {
//                        super.onCreate(savedInstanceState)
//                        datePicker.findViewById<View>(resources.getIdentifier("dayOfMonth", "id", "android")).visibility = View.GONE
//                    }
//                }



                monthDatePickerDialog.setTitle("Select month And Year")
                monthDatePickerDialog.show()

        }


        addPay_bill_current_month.setOnClickListener {
            addPay_bill_select_month_area.visibility = View.GONE
            rentMonth = sdf.format(Date())
        }
        addPay_bill_others_month.setOnClickListener {
            addPay_bill_select_month_area.visibility = View.VISIBLE
           // rentMonth = addPay_bill_select_pay_month.text.toString()
        }


        addPay_bill_add_button.setOnClickListener {
            addPayBillForAMOnth()
        }

        getTenantByID()
    }

    private fun getTenantByID() {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    showFlatInfoAtStart(user!!.getName())
                }
            }
        })
    }
    private fun showFlatInfoAtStart(homeLordName: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var tenantUser = item.getValue(Tenant::class.java)
                        if(tenantUser!!.getTenantUserName().equals(homeLordName)){
                            addPay_bill_hlname.text = tenantUser.getTenantUserName()
                            addPay_bill_flat_name.text = tenantUser.getFlatName()
                            addPay_bill_building_name.text = tenantUser.getPropertyName()
                            addPay_bill_tnt_rent.text = tenantUser.getRent()

                            getHomelordName(tenantUser.getHomeLordId())
                        }
                    }
                }
            }
        })
    }
    private fun getHomelordName(homeLordId: String) {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Users")
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var user = item.getValue(User::class.java)
                        if(user!!.getUid().equals(homeLordId)){
                            addPay_bill_hlname.text = user.getName()
                            homeLordId22 = user.getUid()
                        }
                    }
                }
            }
        })
    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
    private fun addPayBillForAMOnth() {
        var bill_hlname =  addPay_bill_hlname.text.toString()
        var bill_flat_name =  addPay_bill_flat_name.text.toString()
        var bill_building_name =  addPay_bill_building_name.text.toString()
        var bill_rent_amount =  addPay_bill_rent_amount.text.toString()
        rentMonth = addPay_bill_select_pay_month.text.toString()
        when{
            TextUtils.isEmpty(bill_rent_amount) -> showToast("Rent Amount NOT to be NULL")
            TextUtils.isEmpty(rentMonth) -> showToast("Pay rent month NOT to be NULL")
            TextUtils.isEmpty(selectedViaPay) -> showToast("Please select pay via method")

            else -> {
                var rentRef = FirebaseDatabase.getInstance().reference.child("PayRents")
                var rentMap = HashMap<String, Any>()
                var payId = rentRef.push().key
                rentMap["payId"] = payId!!
                rentMap["tenantId"] = firebaseUser!!.uid
                rentMap["homeLordId"] = homeLordId22!!
                rentMap["homeLordName"] = bill_hlname
                rentMap["flatName"] = bill_flat_name
                rentMap["buildingName"] = bill_building_name
                rentMap["paidRentAmount"] = bill_rent_amount
                rentMap["paidRentMonth"] = rentMonth
                rentMap["payViaService"] = selectedViaPay

                rentRef.child(payId).setValue(rentMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        showToast("Your rent bill paid Successfully.")
                        finish()
                    }else{
                        showToast("Error : "+ task.exception.toString())
                    }
                }
            }
        }
    }


}