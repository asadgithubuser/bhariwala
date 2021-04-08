package com.example.bhariwala

import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Property
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_ads.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class AddAdsActivity : AppCompatActivity() {

    private var flatList: ArrayList<String>? = null
    private  var flatAdapter: ArrayAdapter<String>? = null

    private var selectedFlat: Flat? = null

    private var religionName:String? = null
    private var security:String? = null
    private var gas:String? = null
    private var lift:String? = null
    private var generator:String? = null

    var status = ""

    private var homeType:String? = null
    private var rentForWhome:ThemedButton? = null

     var selected_flat = ""

     var hlid  = ""
     var flatid  = ""
     var sf = ""
     var person = ""
     var totroom = ""
     var tbath = ""
     var attbath = ""
     var belc = ""
     var ftype = ""
     var floort = ""
     var rfm = ""
     var rennt = ""
     var ebill = ""
     var mbil = ""
     var gbill = ""
     var wbill = ""
    var property_address = ""
    var property_road = ""
    var property_house = ""
    var property_section = ""
    var property_city = ""
    var property_thana = ""
    var property_division = ""

    private var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ads)


        firebaseUser = FirebaseAuth.getInstance().currentUser

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add New Ad")
        }



        var select_flat = findViewById<AutoCompleteTextView>(R.id.select_flat_in_adPage)
        flatList = ArrayList()
        flatAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, flatList!!)
        select_flat.threshold = 1
        select_flat.setAdapter(flatAdapter)
        select_flat.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            var selectedItem = parent.getItemAtPosition(position).toString()
            selected_flat = selectedItem

            retribeFlatObject(selected_flat)
        }


        retribeAllAds()

        homeTypeButtonToggleGroup.addOnButtonCheckedListener{ group, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.btg_flat -> {
                        homeType = btg_flat.text.toString()
                    }
                    R.id.btg_sublet -> {
                        homeType = btg_sublet.text.toString()
                    }

                    R.id.btg_hostel -> {
                        homeType = btg_hostel.text.toString()
                    }
                }
            }else{
                if(group.checkedButtonId == View.NO_ID){
                    showToast("please select home type")
                }
            }
        }


        var bachelor1 = findViewById<ThemedButton>(R.id.rfwh_bachelor)
        var family1 = findViewById<ThemedButton>(R.id.rfwh_family)
        var small_family1 = findViewById<ThemedButton>(R.id.rfwh_small_family)
        var student_male1 = findViewById<ThemedButton>(R.id.rfwh_studentMale)
        var student_female1 = findViewById<ThemedButton>(R.id.rfwh_studentFemale)
        var jobHolder_male1 = findViewById<ThemedButton>(R.id.rfwh_jobHolderMale)
        var jobHolder_female1 = findViewById<ThemedButton>(R.id.rfwh_jobHolderFemale)
        var single_seat_male1 = findViewById<ThemedButton>(R.id.rfwh_singleSeatMale)
        var single_seat_female1 = findViewById<ThemedButton>(R.id.rfwh_singleSeatFemale)
        var religion_muslim = findViewById<ThemedButton>(R.id.religion_muslim_btn)
        var religion_hindu = findViewById<ThemedButton>(R.id.religion_hindu_btn)
        var religion_buddo = findViewById<ThemedButton>(R.id.religion_buddo_btn)
        var religion_any = findViewById<ThemedButton>(R.id.religion_any_btn)
        var security_yes = findViewById<ThemedButton>(R.id.security_yes_btn)
        var security_no = findViewById<ThemedButton>(R.id.security_no_btn)
        var gas_yes = findViewById<ThemedButton>(R.id.gas_yes_btn)
        var gas_no = findViewById<ThemedButton>(R.id.gas_no_btn)
        var lift_yes = findViewById<ThemedButton>(R.id.lift_yes_btn)
        var lift_no = findViewById<ThemedButton>(R.id.lift_no_btn)
        var generator_yes = findViewById<ThemedButton>(R.id.generator_yes_btn)
        var generator_no = findViewById<ThemedButton>(R.id.generator_no_btn)


        selectRentForWhomeToggleButton.setOnSelectListener { buttonId: ThemedButton ->
            when(buttonId){
                bachelor1 -> { rentForWhome = bachelor1}
                family1 -> { rentForWhome = family1}
                small_family1 -> { rentForWhome = small_family1}
                student_male1 -> { rentForWhome = student_male1}
                student_female1 -> { rentForWhome = student_female1}
                jobHolder_male1 -> { rentForWhome = jobHolder_male1}
                jobHolder_female1 -> { rentForWhome = jobHolder_female1}
                single_seat_male1 -> { rentForWhome = single_seat_male1}
                single_seat_female1 -> { rentForWhome = single_seat_female1}
            }
//            val selectedButtons = selectRentForWhomeToggleButton.selectedButtons
//// get all buttons
//            val allButtons = selectRentForWhomeToggleButton.buttons
//// get all unselected buttons
//            val unselectedButtons = allButtons.filter { !it.isSelected }

        }
        security_toggleBtn_yes_btn.setOnSelectListener { button: ThemedButton ->
            when(button){
                security_yes -> { security = security_yes.text }
                security_no -> { security = security_no.text }
            }
        }
        gas_toggleBtn_yes_btn.setOnSelectListener { button: ThemedButton ->
            when(button){
                gas_yes -> { gas = gas_yes.text }
                gas_no -> { gas =  gas_no.text }
            }
        }
        lift_toggleBtn_yes_btn.setOnSelectListener { button: ThemedButton ->
            when(button){
                lift_yes -> { lift = lift_yes.text }
                lift_no -> { lift = lift_no.text }
            }
        }
        generator_toggleBtn_yes_btn.setOnSelectListener { button: ThemedButton ->
            when(button){
                generator_yes -> { generator = generator_yes.text }
                generator_no -> { generator = generator_no.text }
            }
        }

        religion_name_toggleBtn.setOnSelectListener { button: ThemedButton ->
            when(button){
                religion_muslim -> { religionName = religion_muslim.text }
                religion_hindu -> { religionName = religion_hindu.text }
                religion_buddo -> { religionName = religion_buddo.text }
                religion_any -> { religionName = religion_any.text }
            }
        }



        //============= add new ad operation =============
        addads_add_ad_button.setOnClickListener {
            var ad_title = addads_ad_title.text.toString()
            var ad_boutHome = addads_ad_about_home.text.toString()

            when{
                TextUtils.isEmpty(ad_title) -> showToast("ad title should not be null")
                TextUtils.isEmpty(ad_boutHome) -> showToast("about Home should not be null")
                TextUtils.isEmpty(homeType) -> showToast("home Type should not be null")
                TextUtils.isEmpty(rentForWhome.toString()) -> showToast("rent ForW home should not be null")
                TextUtils.isEmpty(selected_flat) -> showToast("flat name should not be null")
                TextUtils.isEmpty(security) -> showToast("security service should not be null")
                TextUtils.isEmpty(gas) -> showToast("gas service should not be null")
                TextUtils.isEmpty(lift) -> showToast("lift service should not be null")
                TextUtils.isEmpty(generator) -> showToast("generator service should not be null")
                TextUtils.isEmpty(religionName) -> showToast("Religion Name should not be null")

                else -> {
                    var progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Add New Ad")
                    progressDialog.setMessage("Please wait, we are take sometimes for adding to new ad. ")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()


                   var addRef = FirebaseDatabase.getInstance().reference.child("Ads")
                   var adId = addRef.push().key
                   var AdMap = HashMap<String, Any>()
                   AdMap["adId"] = adId!!
                   AdMap["adTitle"] = ad_title
                   AdMap["adBoutHome"] = ad_boutHome
                   AdMap["homeType"] = homeType!!
                   AdMap["flatName"] = selected_flat
                   AdMap["rentForWhome"] = rentForWhome!!.text
                   AdMap["security"] = security!!
                   AdMap["gas"] = gas!!
                   AdMap["lift"] = lift!!
                   AdMap["generator"] = generator!!
                   AdMap["religionName"] = religionName!!

                    //====== from flat
                   AdMap["homeLordId"] = hlid
                   AdMap["flatId"] = flatid
                   AdMap["squareFeet"] = sf
                   AdMap["persons"] = person
                   AdMap["totalRooms"] = totroom
                   AdMap["totalBaths"] = tbath
                   AdMap["attachedBath"] = attbath
                   AdMap["belcony"] = belc
                   AdMap["furnistType"] = ftype
                   AdMap["floorType"] = floort
                   AdMap["rentForMonth"] = rfm
                   AdMap["rent"] = rennt
                   AdMap["electricityBill"] = ebill
                   AdMap["maintanenceBill"] = mbil
                   AdMap["gasBill"] = gbill
                   AdMap["waterBill"] = wbill

                    //======== from property
//                   AdMap["propertyAddress"] = property_address
//                   AdMap["propertyRoad"] = property_road
//                   AdMap["propertyHouse"] = property_house
//                   AdMap["propertySection"] = property_section
//                   AdMap["propertyCity"] = property_city
//                   AdMap["propertyThana"] = property_thana
//                   AdMap["propertyDivision"] = property_division


                   addRef.child(adId).setValue(AdMap).addOnCompleteListener { task->
                       if (task.isSuccessful){
                           progressDialog.dismiss()
                           showToast("New Ad has been added successfully")
                           finish()
                       }else{
                           progressDialog.dismiss()
                           showToast("Error: "+task.exception.toString())
                       }
                   }

                }
            }
        }



    }


    private fun retribeFlatObject(selectedFlatName: String) {

        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats")
        flatRef.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flatItem in snapshot.children){
                        var flat = flatItem.getValue(Flat::class.java)
                        if(flat!!.getFlatName() == selectedFlatName){

                            hlid = flat.getHomeLordId()
                            flatid = flat.getFlatId()
                            sf = flat.getSquareFeet()
                            person = flat.getPersons()
                            totroom = flat.getTotalRooms()
                            tbath = flat.getTotalBaths()
                            attbath = flat.getAttachedBath()
                            belc = flat.getBelcony()
                            ftype = flat.getFurnistType()
                            floort  = flat.getFloorType()
                            rfm = flat.getRentForMonth()
                            rennt = flat.getRent()
                            ebill = flat.getElectricityBill()
                            mbil = flat.getMaintanenceBill()
                            gbill = flat.getGasBill()
                            wbill = flat.getWaterBill()

                            //getFromPropertyByFlatID(flat.getPropertyId())
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getFromPropertyByFlatID(propertyId: String) {
        var propertyRef = FirebaseDatabase.getInstance().getReference().child("Properties")
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (property in snapshot.children){
                        var propertyItem = property.getValue(Property::class.java)
                        if(propertyItem!!.getHomeLordId().equals(firebaseUser!!.uid)){
                           property_address = propertyItem.getAddress()
                           property_road = propertyItem.getRoad()
                           property_house = propertyItem.getHouse()
                           property_section = propertyItem.getSection()
                           property_city = propertyItem.getCity()
                           property_thana = propertyItem.getThana()
                           property_division = propertyItem.getDivision()
                        }
                    }
                }
            }
        } )
    }

    private fun retribeAllAds() {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getHomeLordId().equals(firebaseUser!!.uid)){
                            flatList?.add(flatItem.getFlatName())
                        }
                    }
                    flatAdapter?.notifyDataSetChanged()
                }
            }
        } )
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()

    }
}