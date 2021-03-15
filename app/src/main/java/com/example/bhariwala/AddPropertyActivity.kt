package com.example.bhariwala

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_flat.*
import kotlinx.android.synthetic.main.activity_add_property.*

class AddPropertyActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    var spinner: Spinner? = null
    private var firebaseUser: FirebaseUser? = null
    var division = ""
    var divisions = arrayOf("Dhaka", "Mymensingh", "Barishal", "Chittagonge", "Khulna", "Rajshahi", "Rangpur", "Sylhet")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        //setSupportActionBar(property_toolbar_id)
//        var actionBar = getSupportActionBar()
//        if(actionBar != null){
//            actionBar.setTitle("Add Property")
//            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left)
//        }

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add Property")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, divisions)
        add_property_divisions.threshold = 1
        add_property_divisions.setAdapter(arrayAdapter)
        add_property_divisions.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var selectedDivision = parent.getItemAtPosition(position).toString()

            division = selectedDivision
        }


        add_property_btn.setOnClickListener {
            var b_name = pro_building_name.text.toString()
            var total_floor = pro_total_floor_of_building.text.toString()
            var address = pro_leaf_address.text.toString()
            var road = pro_road_no.text.toString()
            var house = pro_house_no.text.toString()
            var section = pro_section_a.text.toString()
            var thana = pro_thana.text.toString()
            var postCode = pro_postCode.text.toString()
            var city = pro_city.text.toString()

            when{
                TextUtils.isEmpty(b_name) -> Toast.makeText(this, "Building Name should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(total_floor) -> Toast.makeText(this, "Building total floor should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(address) -> Toast.makeText(this, "Address should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(road) -> Toast.makeText(this, "Road No should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(house) -> Toast.makeText(this, "House No should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(section) -> Toast.makeText(this, "Section should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(thana) -> Toast.makeText(this, "Thana should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(postCode) -> Toast.makeText(this, "PostCode should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(city) -> Toast.makeText(this, "City should not be null", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(division) -> Toast.makeText(this, "Division should not be null", Toast.LENGTH_LONG).show()


                else -> {
                    var progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Add Property")
                    progressDialog.setMessage("please wait, adding a new property....")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    var propertyRef = FirebaseDatabase.getInstance().reference.child("Properties")

                    var propertyId = propertyRef.push().key
                    var propertyMap = HashMap<String, Any>()

                    propertyMap["propertyId"] = propertyId!!
                    propertyMap["homeLordId"] = firebaseUser!!.uid
                    propertyMap["buildingName"] = b_name
                    propertyMap["totalFloor"] = total_floor
                    propertyMap["address"] = address
                    propertyMap["road"] = road
                    propertyMap["house"] = house
                    propertyMap["section"] = section
                    propertyMap["thana"] = thana
                    propertyMap["postCode"] = postCode
                    propertyMap["city"] = city
                    propertyMap["division"] = division

                    propertyRef.child(propertyId).setValue(propertyMap).addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            Toast.makeText(this@AddPropertyActivity, "Property Added Successfully", Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()
                            finish()
                        }else{
                            progressDialog.dismiss()
                            Toast.makeText(this@AddPropertyActivity, "Erro: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this.applicationContext, "Selected: "+divisions[position], Toast.LENGTH_LONG).show()
    }
}

