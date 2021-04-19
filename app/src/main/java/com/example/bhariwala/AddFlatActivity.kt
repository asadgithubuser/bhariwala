package com.example.bhariwala

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.UploadFlatImagesAdapter
import com.example.bhariwala.Models.Property
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
import kotlinx.android.synthetic.main.activity_add_flat.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddFlatActivity : AppCompatActivity(){

    //==== uplaod flat images
    private var context: Context? = null
    private var imgUri: Uri? = null
    private var uploadFlatImages: ArrayList<Uri>? = null
    var imageEncoded: Bitmap? = null
    var PICK_IMAGE_CODE = 0
    var imagesEncodedList: ArrayList<Bitmap>? = null
    var flatImgcount = 0
    var flat_name = ""
    var flatData: Intent?= null
    lateinit var imagePath: String
    var imagesPathList: ArrayList<String>? = null

    private var storageRef : StorageReference? = null

    private var upFlatImgAdapter: UploadFlatImagesAdapter? = null

    //var flatImageSwitcher : ImageSwitcher? = null
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

        storageRef = FirebaseStorage.getInstance().reference

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
            saveNewFlatInfo()

        }


        getAllBuildingNames()

       // val images = intArrayOf(R.drawable.profile, R.drawable.add_image, R.drawable.signature)

        imagesPathList = ArrayList()
        uploadFlatImages = ArrayList()
        imagesEncodedList = ArrayList()
        //var mImagelist:List<Int> = listOf(R.drawable.profile, R.drawable.add_image)
        //flatImageSwitcher2.setFactory{ImageView(applicationContext)}



        var recyclerView = findViewById<RecyclerView>(R.id.flatImgRecyclerview)
      // var recyclerView = findViewById<ImageSwitcher>(R.id.flatImageSwitcher)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        upFlatImgAdapter = UploadFlatImagesAdapter(this, imagesEncodedList as MutableList<Bitmap>)
        recyclerView.adapter = upFlatImgAdapter


        uploadFlatImages_Btn.setOnClickListener {
            pickImagesInten()


        }

    }

    //========== take multiple imagese => me
    private fun pickImagesInten(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select imgae(s)"), PICK_IMAGE_CODE)
    }

    fun loadFromUri(photoUri: Uri?): Bitmap? {
        var image: Bitmap? = null
        try {
            // check version of Android on device
            image = if (Build.VERSION.SDK_INT > 27) {

                //=1
                 BitmapFactory.decodeFile(photoUri!!.path)

                //=2
//                val source: ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver, photoUri!!)
//                ImageDecoder.decodeBitmap(source)
            } else {
                // support older versions of Android by using getBitmap
                MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }


    fun loadFromPath(filePath: Uri?) :String{
        var result: String? = null

        if(filePath!!.scheme!!.equals("content")){
            val cursor: Cursor? = contentResolver.query(filePath, null, null, null, null)
            // Move to first row
            try {
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }finally {
                cursor!!.close()
            }

            if(result == null){
                result = filePath.path
            }
        }

        return result!!
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        if(requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK && data!!.clipData != null){
            flatImgcount = data.clipData!!.itemCount
            flatData = data
            imagesEncodedList!!.clear()

            flatImgViewPager.visibility = View.GONE

            Log.d("hhg", flatImgcount.toString())
            for (i in 0 until flatImgcount) {
                 imgUri = data.clipData!!.getItemAt(i).uri

                val selectedImage: Bitmap? = loadFromUri(imgUri!!)
                imagesEncodedList!!.add(selectedImage!!)


//            val cursor: Cursor? = contentResolver.query(imguri!!, filePathColumn, null, null, null)
//            cursor!!.moveToFirst()
//            val columnIndex: Int = cursor.getColumnIndex(filePathColumn.get(0))
//            imageEncoded = cursor.getString(columnIndex)
//            imagesEncodedList!!.add(imageEncoded!!)
//           flatImgViewPager.setImageURI(imguri22)



            }
            upFlatImgAdapter!!.notifyDataSetChanged()

        }else{
           flatImgViewPager.visibility = View.VISIBLE
            val imguri22 = data!!.data!!
            val selectedImage: Bitmap? = MediaStore.Images.Media.getBitmap(contentResolver, imguri22)
            flatImgViewPager.setImageBitmap(selectedImage)

        }
    }


//
//    private fun getPathFromURI(uri: Uri) {
//        var path: String = uri.path.toString() // uri = any content Uri
//        val root: String = Environment.getExternalStorageDirectory().toString()
//
//        val databaseUri: Uri
//        val selection: String?
//        val selectionArgs: Array<String>?
//        if (path.contains("$root/saved_picture:")) { // files selected from "Documents"
//            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            selection = "_id=?"
//            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
//        } else { // files selected from all other sources, especially on Samsung devices
//            databaseUri = uri
//            selection = null
//            selectionArgs = null
//        }
//        try {
//            val projection = arrayOf(
//                    MediaStore.Images.Media.DATA,
//                    MediaStore.Images.Media._ID,
//                    MediaStore.Images.Media.ORIENTATION,
//                    MediaStore.Images.Media.DATE_TAKEN
//            ) // some example data you can query
//            val cursor = contentResolver.query(
//                    databaseUri,
//                    projection, selection, selectionArgs, null
//            )
//            if (cursor!!.moveToFirst()) {
//                val columnIndex = cursor.getColumnIndex(projection[0])
//                imagePath = cursor.getString(columnIndex)
//                // Log.e("path", imagePath);
//
//                Log.d("ihhh", "path -> "+imagePath)
//                imagesPathList.add(imagePath)
//            }
//            cursor.close()
//        } catch (e: Exception) {
//            Log.e("Error: ", e.message, e)
//        }
//    }







    // select multiple flat images and show
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        // When an Image is picked
//        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
//                && null != data
//        ) {
//            if (data.getClipData() != null) {
//                var count = data.clipData!!.itemCount
//                for (i in 0..count - 1) {
//                    var imageUri: Uri = data.clipData!!.getItemAt(i).uri
//                    getPathFromURI(imageUri)
//                }
//            } else if (data.getData() != null) {
//                var imagePath: String = data.data!!.path!!
//                Log.d("imagePath", imagePath);
//            }
//
//            //displayImageData()
//        }
//    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }


    private fun saveNewFlatInfo() {
        var buildingName = buildingnameShow!!.text.toString()
        flat_name = flat_name_text.text.toString()
        var sq_feet = sq_feet.text.toString()
        var av_persons = av_persons.text.toString()
        var t_rooms = t_rooms.text.toString()
        var t_baths = t_baths.text.toString()
        var attaced_bath = attaced_bath.text.toString()
        var belcony = belcony.text.toString()
        var rent_for_month = rent_for_month.text.toString()
        var rent = rent.text.toString()


        when{
            TextUtils.isEmpty(buildingName) -> showToast("Select building name first")
            TextUtils.isEmpty(furnistType) -> showToast("Select furnist type ")
            TextUtils.isEmpty(floorType2) -> showToast("Select floor type first")
            TextUtils.isEmpty(flat_name) -> showToast("Enter flat name")
            TextUtils.isEmpty(sq_feet) -> showToast("Flat Square Feet Should not be empty")
            TextUtils.isEmpty(av_persons) -> showToast("Persons Should not be empty")
            TextUtils.isEmpty(t_rooms) -> showToast("Total Rooms Should not be empty")
            TextUtils.isEmpty(t_baths) -> showToast("Total Baths Should not be empty")
            TextUtils.isEmpty(attaced_bath) -> showToast("Attached Bath Should not be empty")
            TextUtils.isEmpty(belcony) -> showToast("Belcony Should not be empty")
            TextUtils.isEmpty(rent_for_month) -> showToast("Rent for month Should not be empty")
            TextUtils.isEmpty(rent) -> showToast("Flat Rent Should not be empty")
            TextUtils.isEmpty(electricityBill) -> showToast("Electricity Bill Should not be empty")
            TextUtils.isEmpty(maintanenceBill) -> showToast("Maintanence Bill Should not be empty")
            TextUtils.isEmpty(gasBill) -> showToast("Gas Bill Should not be empty")
            TextUtils.isEmpty(waterBill) -> showToast("Water Bill Should not be empty")

            else -> {
                for (i in 0 until flatImgcount) {
                    var imgName = System.currentTimeMillis().toString()+"($i)"+".jpg"
                    var flatImgStorage = storageRef!!.child(flat_name).child(imgName)

                    imgUri = flatData!!.clipData!!.getItemAt(i).uri
                    uploadFlatImages!!.add(imgUri!!)
                    var uploadTask: StorageTask<*>
                    uploadTask = flatImgStorage.putFile(uploadFlatImages!![i])


                    uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }

                        return@Continuation flatImgStorage.downloadUrl
                    }).addOnCompleteListener( OnCompleteListener{ task ->
                        if (task.isSuccessful){
                            uploadFlatImages!!.add(task.result!!)
                        }
                    })
                }


                Log.d("gggg", uploadFlatImages.toString())

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
                flatMap["propertyId"] = propertyId!!
                flatMap["propertyName"] = buildingName
                flatMap["flatName"] = flat_name
                flatMap["isBooked"] = "0"
                flatMap["flatImages"] = uploadFlatImages.toString()
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

                flatRef.child(flatId).setValue(flatMap).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Flat has been added successfully.", Toast.LENGTH_LONG).show()
                        // finish()
                        progressDialog.dismiss()
                    } else {
                        Toast.makeText(this, "Error: " + task.exception.toString(), Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()
                        startActivity(Intent(this, AddFlatActivity::class.java))
                    }
                }

            }
        }
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


