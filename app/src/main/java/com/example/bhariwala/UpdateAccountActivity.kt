package com.example.bhariwala

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_update_account.*


class UpdateAccountActivity : AppCompatActivity() {

    var userImgUri : Uri? = null
    var storage : StorageReference? = null
    var selectDivision = ""
    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)



        UpdateAcc_add_take_photo.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this);
        }


        var divisions = arrayOf("Dhaka", "Khulna", "Ragnpur", "Dinajpur", "Mymensingh")

        storage = FirebaseStorage.getInstance().reference.child("Update UserImages")
        currentUser = FirebaseAuth.getInstance().currentUser

        var seledd = findViewById<AutoCompleteTextView>(R.id.UpdateAcc_divisions)
        var arrAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, divisions!!)
        seledd.threshold = 1
        seledd.setAdapter(arrAdapter)
        seledd.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            selectDivision = parent.getItemAtPosition(position).toString()
        }


        UpdateAcc_updateBtn.setOnClickListener {
            updateAccountData()
        }

    }

//    file:///data/user/0/com.example.bhariwala/cache/cropped2090736468475097766.jpg
//    file:///storage/emulated/0/IMO/IMO images/G4nHErrDPDZlWTHtsCeUDJeZfgA.jpg

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            var resultUri = CropImage.getActivityResult(data)
            userImgUri = resultUri.uri

            Log.d("crppp", userImgUri.toString())
            UpdateAcc_add_parker_image_view.setImageURI(userImgUri)
        }
    }


    private fun updateAccountData() {
        var name = UpdateAcc_name.text.toString()
        var phone = UpdateAcc_phone.text.toString()
        var about_user = UpdateAcc_about_user.text.toString()
        var address = UpdateAcc_address.text.toString()
        var road_no = UpdateAcc_road_no.text.toString()
        var house_no = UpdateAcc_house_no.text.toString()
        var section = UpdateAcc_section.text.toString()
        var thana = UpdateAcc_thana.text.toString()
        var postCode = UpdateAcc_postCode.text.toString()
        var city = UpdateAcc_city.text.toString()

        when{
            TextUtils.isEmpty(userImgUri.toString()) -> showToast("Please select your image first")
            TextUtils.isEmpty(name) -> showToast("name must not be null")
            TextUtils.isEmpty(phone) -> showToast("phone must not be null")
            TextUtils.isEmpty(about_user) -> showToast("about user must not be null")
            TextUtils.isEmpty(address) -> showToast("address must not be null")
            TextUtils.isEmpty(road_no) -> showToast("road no must not be null")
            TextUtils.isEmpty(house_no) -> showToast("house no must not be null")
            TextUtils.isEmpty(section) -> showToast("section must not be null")
            TextUtils.isEmpty(thana) -> showToast("thana must not be null")
            TextUtils.isEmpty(postCode) -> showToast("postCode must not be null")
            TextUtils.isEmpty(city) -> showToast("city must not be null")
            TextUtils.isEmpty(selectDivision) -> showToast("divisions must not be null")

            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Updating Account")
                progressDialog.setMessage("please wait, updating your account data")
                progressDialog.show()

                var StorRef = storage!!.child(System.currentTimeMillis().toString()+"_user.jpg")

                var uploadTask: StorageTask<*>
                uploadTask = StorRef.putFile(userImgUri!!)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                    if(!task.isSuccessful){
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation StorRef.downloadUrl
                }).addOnCompleteListener(OnCompleteListener{ task ->
                    if(task.isSuccessful){
                        userImgUri = task.result

                        Log.d("ggg", userImgUri.toString())

                        var databaseRef = FirebaseDatabase.getInstance().reference.child("AccountDetails")
                        var AccMap = HashMap<String, Any>()
                        var uAccId = databaseRef.push().key

                        AccMap["userId"] = currentUser!!.uid
                        AccMap["phone"] = phone
                        AccMap["aboutUser"] = about_user
                        AccMap["address"] = address
                        AccMap["road_no"] = road_no
                        AccMap["house_no"] = house_no
                        AccMap["section"] = section
                        AccMap["thana"] = thana
                        AccMap["postCode"] = postCode
                        AccMap["city"] = city
                        AccMap["divisions"] = selectDivision

                        databaseRef.child(uAccId!!).setValue(AccMap).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Account Updated Successfully", Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()

                                updateOldUserData(name, userImgUri!!.toString())
                               // finish()
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

    private fun updateOldUserData(name: String, imageUrl: String) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users")
        var updateMap = HashMap<String, Any>()

        updateMap["name"] = name
        updateMap["image"] = imageUrl

        userRef.child(currentUser!!.uid).updateChildren(updateMap)
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }




}