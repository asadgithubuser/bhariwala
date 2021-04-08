package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_service_man.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class AddServiceManActivity : AppCompatActivity() {

    var selectedSrvcmnType= ""
    var selected_servicemn_duty = ""
    var type_others :TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service_man)




        var duty_fullTime = findViewById<ThemedButton>(R.id.servicemn_duty_fullTime)
        var duty_partTime = findViewById<ThemedButton>(R.id.servicemn_duty_partTime)

        servicemn_duty_selection_buttons.setOnSelectListener { buttonId: ThemedButton ->
            when(buttonId){
                duty_fullTime -> {selected_servicemn_duty = duty_fullTime.text}
                duty_partTime -> {selected_servicemn_duty = duty_partTime.text}
            }
        }

      var type_driver = findViewById<ThemedButton>(R.id.servicemn_type_driver)
      var type_cartaker = findViewById<ThemedButton>(R.id.servicemn_type_cartaker)
      var type_manager = findViewById<ThemedButton>(R.id.servicemn_type_manager)
      var type_scrity_guard = findViewById<ThemedButton>(R.id.servicemn_type_scrity_guard)
      var type_others = findViewById<TextInputEditText>(R.id.servicemn_type_other_name)



        selectServiceMansType.setOnSelectListener { typeId: ThemedButton ->
            when(typeId){
                servicemn_type_driver -> { selectedSrvcmnType = type_driver.text}
                servicemn_type_cartaker -> { selectedSrvcmnType = type_cartaker.text}
                servicemn_type_manager -> { selectedSrvcmnType = type_manager.text}
                servicemn_type_scrity_guard -> { selectedSrvcmnType = type_scrity_guard.text}
                //servicemn_type_other_name -> { selectedSrvcmnType = type_others}
            }
        }



        servicemn_add_button.setOnClickListener {
            saveNewServiceMan()
        }
    }

    private fun saveNewServiceMan() {
        var servicemn_name = servicemn_name.text.toString()
        var servicemn_age = servicemn_age.text.toString()
        var servicemn_salary = servicemn_salary.text.toString()
        var servicemn_fullAddress = servicemn_fullAddress.text.toString()
        var servicemn_phone_no = servicemn_phone_no.text.toString()
        var servicemn_joined_date = servicemn_joined_date.text.toString()
       // var selected_servicemn_duty = selected_servicemn_duty
        //var selectedSrvcmnType = selectedSrvcmnType

        when{
            TextUtils.isEmpty(servicemn_name) -> showToast(" please enter name")
            TextUtils.isEmpty(servicemn_phone_no) -> showToast(" please enter phone number")
            TextUtils.isEmpty(servicemn_joined_date) -> showToast(" please enter date")
            TextUtils.isEmpty(selectedSrvcmnType.toString()) -> showToast(" please enter serviceman type")
            TextUtils.isEmpty(servicemn_age) -> showToast(" please enter age")
            TextUtils.isEmpty(servicemn_salary) -> showToast(" please enter salary")
            TextUtils.isEmpty(servicemn_fullAddress) -> showToast(" please enter full address")
            TextUtils.isEmpty(selected_servicemn_duty.toString()) -> showToast(" please enter duty type")

            else ->{
                var ref = FirebaseDatabase.getInstance().reference.child("Servicemans")
                var servicemanId = ref.push().key
                var SMap = HashMap<String, Any>()
                SMap["servicemanId"] = servicemanId!!
                SMap["homelordId"] = FirebaseAuth.getInstance().currentUser.uid
                SMap["servicemnName"] =  servicemn_name
                SMap["servicemnPhone"] =  servicemn_phone_no
                SMap["servicemnJoinDate"] =  servicemn_joined_date
                SMap["servicemnAge"] =  servicemn_age
                SMap["servicemnSalary"] =  servicemn_salary
                SMap["servicemnFullAddress"] =  servicemn_fullAddress
                SMap["selectedSrvcmnType"] =  selectedSrvcmnType.toString()
                SMap["selectedServicemnDuty"] =  selected_servicemn_duty.toString()

                ref.child(servicemanId).setValue(SMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        showToast("A serviceman added successfully")
                        finish()
                    }else{
                        showToast("Error: "+task.exception.toString())
                    }
                }
            }
        }

    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}