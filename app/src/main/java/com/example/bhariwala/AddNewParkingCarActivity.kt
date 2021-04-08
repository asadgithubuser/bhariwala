package com.example.bhariwala

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bhariwala.classes.SignatureMainLayout
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_new_parking_car.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddNewParkingCarActivity : AppCompatActivity() {

    var homelordId :String? = null
    var selectedParker = ""
    var selectedCarType = ""
    var cTime = ""
    var cDate = ""
    private var storage : StorageReference? = null
    var parkerImageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_parking_car)


        storage = FirebaseStorage.getInstance().reference.child("Car Parker Image")

        homelordId = intent?.getStringExtra("homelordId")

        carPark_add_parker_groupType.setOnSelectListener { buttonId : ThemedButton ->
            when(buttonId){
                findViewById<ThemedButton>(R.id.carPark_parkerType_guest) -> { selectedParker = buttonId.text}
                findViewById<ThemedButton>(R.id.carPark_parkerType_relative) -> { selectedParker = buttonId.text}
                findViewById<ThemedButton>(R.id.carPark_parkerType_resident) -> { selectedParker = buttonId.text}
                findViewById<ThemedButton>(R.id.carPark_parkerType_other) -> { selectedParker = buttonId.text}
            }

        }
        carPark_add_car_type_btnGroup.setOnSelectListener { buttonId : ThemedButton ->
            when(buttonId){
                findViewById<ThemedButton>(R.id.carPark_carType_car) -> { selectedCarType = buttonId.text}
                findViewById<ThemedButton>(R.id.carPark_carType_motor_cycle) -> { selectedCarType = buttonId.text}
                findViewById<ThemedButton>(R.id.carPark_carType_rikshaw) -> { selectedCarType = buttonId.text}
                findViewById<ThemedButton>(R.id.carPark_carType_others) -> { selectedCarType = buttonId.text}
            }

        }

        carPark_add_take_signature.setOnClickListener {
            setContentView(SignatureMainLayout(this))
        }




        carPark_add_take_photo.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this);
        }

//        var currentTime = LocalTime.now()
//        cTime = currentTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
//
//        var currentDate = LocalDate.now()
//        cDate = currentTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))

        var dtime = LocalDateTime.now()
        var dateTime = dtime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT))
        carPark_add_in_time.setText(dateTime)




        carPark_add_addBtn.setOnClickListener {
            saveAaCarInPark()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            var result = CropImage.getActivityResult(data)
            parkerImageUri = result.uri

            carPark_add_parker_image_view.setImageURI(parkerImageUri)
        }
    }

    private fun saveAaCarInPark() {
        var carPark_add_name = carPark_add_name.text.toString()
        var carPark_add_phone = carPark_add_phone.text.toString()
        var carPark_add_cameWhome_details = carPark_add_cameWhome_details.text.toString()
        var carPark_carType_car_number = carPark_carType_car_number.text.toString()
        var date_time = carPark_add_in_time.text.toString()

        when{
            TextUtils.isEmpty(carPark_add_name) -> showToast(" not be null.")
            TextUtils.isEmpty(carPark_add_phone) -> showToast(" not be null.")
            TextUtils.isEmpty(selectedParker) -> showToast(" not be null.")
            TextUtils.isEmpty(carPark_add_cameWhome_details) -> showToast(" not be null.")
            TextUtils.isEmpty(selectedCarType) -> showToast(" not be null.")
            TextUtils.isEmpty(carPark_carType_car_number) -> showToast(" not be null.")
            parkerImageUri.toString() == "" -> showToast("Please take a parkerman image")


            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Car Parking")
                progressDialog.setMessage("please wait, adding a new car for parking....")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                /// == image save on storage
                var imgRef = storage!!.child(System.currentTimeMillis().toString()+".jpg")

                var uploadTask : StorageTask<*>
                uploadTask = imgRef.putFile(parkerImageUri!!)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if(!task.isSuccessful){
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation imgRef.downloadUrl
                }).addOnCompleteListener ( OnCompleteListener { task ->
                    if(task.isSuccessful){
                        parkerImageUri = task.result


                        var carpRef = FirebaseDatabase.getInstance().reference.child("ParkingCars")
                        var carpId = carpRef.push().key
                        var carMap = HashMap<String, Any>()

                        carMap["carpId"] = carpId!!
                        carMap["homeLordId"] = "homelordId!!"
                        carMap["securityGuardId"] = FirebaseAuth.getInstance().currentUser.uid
                        carMap["parkerName"] = carPark_add_name
                        carMap["parkerPhone"] = carPark_add_phone
                        carMap["parkerType"] = selectedParker
                        carMap["cameDetails"] = carPark_add_cameWhome_details
                        carMap["carType"] = selectedCarType
                        carMap["carNumber"] = carPark_carType_car_number
                        carMap["dateTime"] = date_time
                        carMap["parkerImage"] = parkerImageUri!!.toString()

                        carpRef.child(carpId).setValue(carMap).addOnCompleteListener {task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "New car Added Successfully", Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()
                                finish()
                            }else{
                                progressDialog.dismiss()
                                Toast.makeText(this, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                })
            }
        }



    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }


}