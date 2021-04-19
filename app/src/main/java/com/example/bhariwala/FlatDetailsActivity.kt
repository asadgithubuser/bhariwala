package com.example.bhariwala


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.example.bhariwala.Models.AccountDetail
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_flat_details.*



class FlatDetailsActivity : AppCompatActivity() {

    private var flatId : String? = null
    private var imgPathList: ArrayList<String>? = null
    val imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat_details)

        flatId = intent.getStringExtra("flatId")

        fdetails_homelord_pic.setOnClickListener {
            goToHomelordProfile(flatId)
        }

        retribeFlatData(flatId)
        retribeFlatImages(flatId)

    }

    private fun goToHomelordProfile(flatId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)

                    var intent = Intent(this@FlatDetailsActivity, HomeLordProfileActivity::class.java)
                    intent.putExtra("holeLordId", flat!!.getHomeLordId())
                    startActivity(intent)
                }
            }
        })
    }

    private fun retribeFlatImages(flatId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)
                    retriveImageFromStorage2(flat!!.getFlatName())
                }
            }
        })
    }

    private fun retriveImageFromStorage332(flatName: String, fileName: String) {
        val storageRef = FirebaseStorage.getInstance()
        var storageRef2: StorageReference = storageRef.reference.child(flatName).child(fileName)
        storageRef2.downloadUrl.addOnCompleteListener( OnCompleteListener { task ->

            imageList.add(SlideModel(task.result.toString()))

            val imageSlider = findViewById<ImageSlider>(R.id.flat_image_slider)
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.startSliding(4000)


//            imageSlider.setItemClickListener(object : ItemClickListener {
//                override fun onItemSelected(position: Int) {
//                    var intent = Intent(this@FlatDetailsActivity, FlatImageSliderAcitivity::class.java)
//                    intent.putExtra("flatImageList", imageList)
//                    intent.putExtra("imageListPosition", position)
//                    startActivity(intent)
//                }
//            })


        }).addOnFailureListener {
            it.stackTrace
        }
    }


 private fun retriveImageFromStorage2(flatName: String) {
     val firebaseStorage = FirebaseStorage.getInstance().reference
     val storageRef: StorageReference = firebaseStorage.child(flatName)

     storageRef.listAll().addOnSuccessListener { result ->

                result.items.forEach { storageRef ->
//                   storageRef.downloadUrl.addOnSuccessListener { uri ->
//                       // Log.d("ikkk", "path -> "+uri.path.toString())
//                   }
                    retriveImageFromStorage332(flatName, storageRef.name)

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Unable to fetch items!", Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            }
    }


    private fun retriveImageFromStorage33(flatName: String) {
        Log.d("ikkk", "one")
        var flatImgRef = FirebaseStorage.getInstance().reference.child(flatName)
        flatImgRef.listAll()
                .addOnSuccessListener { (items, prefixes) ->
                    Log.d("ikkk", "one22")
                    prefixes.forEach { prefix ->
                        // All the prefixes under listRef.
                        // You may call listAll() recursively on them.
                    }

                    items.forEach { item ->
                        Log.d("ikkk", item.downloadUrl.toString())
                    }
                }
                .addOnFailureListener {
                    // Uh-oh, an error occurred!
                }




//        Log.d("immg", flatImgRef.toString())
//        var image_slider = findViewById<ImageView>(R.id.image_slider)
//      Glide.with(this).load("gs://bhariwala-f7aa6.appspot.com/flat 44/1618548393624(0).jpg").into(image_slider)


    }


    private fun retribeFlatData(flatId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)

                    flat_name_in_details.text = flat!!.getFlatName()
                    d_rent_month.text = flat!!.getRentForMonth()
                    d_total_room.text =  "Total rooms "+flat!!.getTotalRooms()
                    d_total_bath.text =  "Total baths "+flat!!.getTotalBaths()
                    d_attached_bath.text = "Attached bath "+flat!!.getAttachedBath()
                    d_eletricity_bill.text = "Electricity - "+flat!!.getElectricityBill()
                    d_water_bill.text = "Water - "+flat!!.getWaterBill()
                    d_gas_bill.text = "Gas - "+flat!!.getGasBill()
                    d_service_bill.text = "Maintanence - "+flat!!.getMaintanenceBill()


                    getFlatHomeloardData(flat.getHomeLordId())

                }
            }
        })
    }

    private fun getFlatHomeloardData(homeLordId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Users").child(homeLordId)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    fdetails_homelord_name.text = user!!.getName()
                    Picasso.get().load(user.getImage()).into(fdetails_homelord_pic)

                    getFlatHomeloardDataMore(user.getUid())

                }
            }
        })
    }


    private fun getFlatHomeloardDataMore(homeLordId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("AccountDetails")
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var userDetails = item.getValue(AccountDetail::class.java)
                        if(userDetails!!.getUserId().equals(homeLordId)){
                            fdetails_homelord_phone.text = "Phone: "+userDetails.getPhone()
                        }
                    }

                }
            }
        })
    }


}


