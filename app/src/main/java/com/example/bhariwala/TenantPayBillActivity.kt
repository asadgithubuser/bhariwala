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
import com.example.bhariwala.Models.Flat
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
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
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

     var currentTime = ""
    var currentDate = ""


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


        var cDate = SimpleDateFormat("dd/MM/yyyy")
        currentDate = cDate.format(Date())

        var cTime = LocalDateTime.now()
        currentTime = cTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

        //======get current month
        val myFormat = "MMMM yyyy"
        val sdf = SimpleDateFormat(myFormat)


        addPay_bill_select_pay_month.setOnClickListener {
//            var datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
//                    view, myear, mmonth, mdayOfMonth ->
//                addPay_bill_select_pay_month.setText(""+mdayOfMonth+"/"+mmonth+"/"+myear)
//            }, year, month, day)
//            datePicker.show()

                val mcurrentDate = Calendar.getInstance()

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

        rentMonth = sdf.format(Date())
        addPay_bill_current_month.setOnClickListener {
            addPay_bill_select_month_area.visibility = View.GONE
            rentMonth = sdf.format(Date())
        }
        addPay_bill_others_month.setOnClickListener {
            addPay_bill_select_month_area.visibility = View.VISIBLE
           rentMonth = addPay_bill_select_pay_month.text.toString()
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
                    showFlatInfoAtStart(user!!.getUid())
                }
            }
        })
    }
    private fun showFlatInfoAtStart(homeLordId: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var tenantUser = item.getValue(Tenant::class.java)
                        if(tenantUser!!.getTenantId().equals(homeLordId)){
                            getTenantDetails(tenantUser.getFlatId(), tenantUser.getHomeLordId())
                        }
                    }
                }
            }
        })
    }

    private fun getTenantDetails(flatId: String, homeLordId: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        tenantRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)
                        addPay_bill_flat_name.text = flat!!.getFlatName()
                        addPay_bill_building_name.text = flat.getPropertyName()
                        addPay_bill_tnt_rent.text = flat.getRent()

                       getHomelordName(homeLordId)
                }
            }
        })
    }

    private fun getHomelordName(homeLordId: String) {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("Users").child(homeLordId)
        bulidingRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                        addPay_bill_hlname.text = user!!.getName()
                        homeLordId22 = user.getUid()
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
        var trxID =  addPay_bill_TrxID.text.toString()
        var rentForMOnth = rentMonth
        when{
            TextUtils.isEmpty(bill_rent_amount) -> showToast("Rent Amount NOT to be NULL")
            TextUtils.isEmpty(rentForMOnth) -> showToast("Pay rent month NOT to be NULL")
            TextUtils.isEmpty(selectedViaPay) -> showToast("Please select pay via method")

            else -> {
                var rentRef = FirebaseDatabase.getInstance().reference.child("PayRents").child(firebaseUser!!.uid)
                var rentMap = HashMap<String, Any>()
                var payId = rentRef.push().key
                rentMap["payId"] = payId!!
                rentMap["tenantId"] = firebaseUser!!.uid
                rentMap["homeLordId"] = homeLordId22!!
                rentMap["homeLordName"] = bill_hlname
                rentMap["time"] = currentTime
                rentMap["date"] = currentDate
                rentMap["isPending"] = "1"
                rentMap["flatName"] = bill_flat_name
                rentMap["buildingName"] = bill_building_name
                rentMap["paidRentAmount"] = bill_rent_amount
                rentMap["paidRentMonth"] = rentForMOnth
                rentMap["payViaService"] = selectedViaPay
                rentMap["trxID"] = trxID

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