package com.example.bhariwala

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bhariwala.Fragments.AddsFragment
import com.example.bhariwala.Models.Property
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_flat.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddFlatActivity : AppCompatActivity(){
    var item = ""

    var propertyNames :MutableList<String>? = null
    var furnistType = ""
    var buildingName = ""
    var floorType2 = ""
    var electricityBill = ""
    var maintanenceBill = ""
    var gasBill = ""
    var waterBill = ""

    var mCurrentUser : FirebaseUser ? = null

    var propertyId: String? = null
    var buildingnameShow :EditText? = null

    var building_types = arrayOf("Building one", "Building two")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flat)

        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        propertyId = intent.getStringExtra("propertyId")
        buildingnameShow = findViewById(R.id.selected_building_name)

        //setSupportActionBar(property_toolbar_id)
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add Flat")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        rent_for_month.setOnClickListener{
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                 rent_for_month.setText(""+mday+"/"+mmonth+"/"+myear)
            }, year, month, day)
            datePicker.show()
        }

        var selectBuildingName = findViewById<AutoCompleteTextView>(R.id.select_building_type)

        var furnistype = findViewById<AutoCompleteTextView>(R.id.select_furnist_type)
        var furnistArr = resources.getStringArray(R.array.furnist_list)

        var floorType = findViewById<AutoCompleteTextView>(R.id.select_floor_type)
        var floorArr = resources.getStringArray(R.array.floor_types)

        var electricityType = findViewById<AutoCompleteTextView>(R.id.select_electricity_bill_type)
        var electricityArr = resources.getStringArray(R.array.electricity_bills)

        var maintanenceType = findViewById<AutoCompleteTextView>(R.id.select_maintanence_bill_type)
        var maintanenceArr = resources.getStringArray(R.array.maintanence_bill)

        var gasType = findViewById<AutoCompleteTextView>(R.id.select_gas_bill_type)
        var gasArr = resources.getStringArray(R.array.gas_bills)

        var waterType = findViewById<AutoCompleteTextView>(R.id.select_water_bill_type)
        var waterArr = resources.getStringArray(R.array.water_bills)

//        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, building_types)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        autocompleteTextField.setAdapter(adapter)
//        autocompleteTextField.onItemSelectedListener = this

//        selectDropdownFieldFunction(buildingName, buildingsNameArr)
//        selectDropdownFieldFunction(furnistype, furnistArr)
//        selectDropdownFieldFunction(floorType, floorArr)
//        selectDropdownFieldFunction(electricityType, electricityArr)
//        selectDropdownFieldFunction(maintanenceType, maintanenceArr)
//        selectDropdownFieldFunction(gasType, gasArr)
//        selectDropdownFieldFunction(waterType, waterArr)


//        Log.d("bname", propertyNames.toString())
//        getAllBuildingNames()
//
//
//        var adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, propertyNames!!)
//        selectBuildingName.setAdapter(adapter1)
//        selectBuildingName.threshold = 1
//
//        selectBuildingName.onItemClickListener = AdapterView.OnItemClickListener{
//            parent,view,position,id ->
//            val selectedItem = parent.getItemAtPosition(position).toString()
//            // Display the clicked item using toast
//            buildingName = selectedItem
//            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
//        }


        var adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, furnistArr)
        furnistype.setAdapter(adapter2)
        furnistype.threshold = 1

        furnistype.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            furnistType = selectedItem
            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }


        var adapter3 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, floorArr)
        floorType.setAdapter(adapter3)
        floorType.threshold = 1

        floorType.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            floorType2 = selectedItem
            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }


        var adapter4 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, electricityArr)
        electricityType.setAdapter(adapter4)
        electricityType.threshold = 1

        electricityType.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            electricityBill = selectedItem
            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

        var adapter5 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, maintanenceArr)
        maintanenceType.setAdapter(adapter5)
        maintanenceType.threshold = 1

        maintanenceType.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            maintanenceBill = selectedItem
            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

        var adapter6 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, gasArr)
        gasType.setAdapter(adapter6)
        gasType.threshold = 1

        gasType.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            gasBill = selectedItem
            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

        var adapter7 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, waterArr)
        waterType.setAdapter(adapter7)
        waterType.threshold = 1

        waterType.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            waterBill = selectedItem
            // Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }



        add_flat_btn.setOnClickListener {

            var buildingName = buildingnameShow!!.text.toString()
            var flat_name = flat_name.text.toString()
            var sq_feet = sq_feet.text.toString()
            var av_persons = av_persons.text.toString()
            var t_rooms = t_rooms.text.toString()
            var t_baths = t_baths.text.toString()
            var attaced_bath = attaced_bath.text.toString()
            var belcony = belcony.text.toString()
            var rent_for_month = rent_for_month.text.toString()
            var rent = rent.text.toString()
//            var electricity_bill = select_electricity_bill_type.text.toString()
//            var maintanence_bill = select_maintanence_bill_type.text.toString()
//            var gas_bill = select_gas_bill_type.text.toString()
//            var water_bill = select_water_bill_type.text.toString()


            when{
                TextUtils.isEmpty(buildingName) -> Toast.makeText(this, "Select building name first", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(furnistType) -> Toast.makeText(this, "Select furnist type ", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(floorType2) -> Toast.makeText(this, "Select floor type first", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(flat_name) -> Toast.makeText(this, "Enter flat name", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(sq_feet) -> Toast.makeText(this, "Flat Square Feet Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(av_persons) -> Toast.makeText(this, "Persons Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(t_rooms) -> Toast.makeText(this, "Total Rooms Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(t_baths) -> Toast.makeText(this, "Total Baths Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(attaced_bath) -> Toast.makeText(this, "Attached Bath Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(belcony) -> Toast.makeText(this, "Belcony Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(rent_for_month) -> Toast.makeText(this, "Rent for month Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(rent) -> Toast.makeText(this, "Flat Rent Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(electricityBill) -> Toast.makeText(this, "Electricity Bill Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(maintanenceBill) -> Toast.makeText(this, "Maintanence Bill Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(gasBill) -> Toast.makeText(this, "Gas Bill Should not be empty", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(waterBill) -> Toast.makeText(this, "Water Bill Should not be empty", Toast.LENGTH_LONG).show()

                else -> {
                    var progressDialog = ProgressDialog(this)
                        progressDialog.setTitle("Add New Flat")
                        progressDialog.setMessage("Please wait, we are adding your flat item info.")
                        progressDialog.setCanceledOnTouchOutside(false)
                        progressDialog.show()

                    var flatRef = FirebaseDatabase.getInstance().reference.child("Flats")

                    var flatId = flatRef.push().key
                    var flatMap = HashMap<String, Any>()
                        flatMap["flatId"] = flatId!!
                        flatMap["homeLordId"] = mCurrentUser!!.uid
                        flatMap["propertyId"] =  propertyId!!
                        flatMap["propertyName"] = buildingName
                        flatMap["flatName"] = flat_name
                        flatMap["squareFeet"] = sq_feet
                        flatMap["persons"] = av_persons
                        flatMap["totalRooms"] = t_rooms
                        flatMap["totalBaths"] = t_baths
                        flatMap["attachedBath"] = attaced_bath
                        flatMap["belcony"] = belcony
                        flatMap["furnistType"] = furnistType
                        flatMap["floorType"] = floorType2
                        flatMap["rentForMonth"] = rent_for_month
                        flatMap["rent"] = rent
                        flatMap["electricityBill"] = electricityBill
                        flatMap["maintanenceBill"] = maintanenceBill
                        flatMap["gasBill"] = gasBill
                        flatMap["waterBill"] = waterBill

                        flatRef.child(flatId).setValue(flatMap).addOnCompleteListener {task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Flat has been added successfully.", Toast.LENGTH_LONG).show()
                               finish()
                                progressDialog.dismiss()
                            }else{
                                Toast.makeText(this, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()
                                startActivity(Intent(this, AddFlatActivity::class.java))
                            }
                    }
                }
            }

        }


        getAllBuildingNames()

    }

    private fun getAllBuildingNames() {
        var buildingRef = FirebaseDatabase.getInstance().reference.child("Properties").child(propertyId!!)
        buildingRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var property = snapshot.getValue(Property::class.java)
                    buildingnameShow!!.setText(property!!.getBuildingName())
                }
            }
        })
    }

//    private fun selectDropdownFieldFunction(selectField: AutoCompleteTextView, selectItemArray: Array<String>) {
//        var item2 = ""
//        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, selectItemArray)
//        selectField.setAdapter(adapter)
//        selectField.threshold = 1
//
//        selectField.onItemClickListener = AdapterView.OnItemClickListener{
//            parent,view,position,id ->
//            val selectedItem = parent.getItemAtPosition(position).toString()
//            //Toast.makeText(this,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
//            // Display the clicked item using toast
//           // floot_type_select = selectedItem
//            //item = selectedItem
//           // Toast.makeText(this,"Selected item2: $item",Toast.LENGTH_SHORT).show()
//        }
//
//        //return item
//    }



}


