package com.example.bhariwala

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.bhariwala.Models.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ads_details.*
import kotlinx.android.synthetic.main.activity_ads_details.d_attached_bath
import kotlinx.android.synthetic.main.activity_ads_details.d_eletricity_bill
import kotlinx.android.synthetic.main.activity_ads_details.d_gas_bill
import kotlinx.android.synthetic.main.activity_ads_details.d_rent_month
import kotlinx.android.synthetic.main.activity_ads_details.d_service_bill
import kotlinx.android.synthetic.main.activity_ads_details.d_total_bath
import kotlinx.android.synthetic.main.activity_ads_details.d_total_room
import kotlinx.android.synthetic.main.activity_ads_details.d_water_bill
import kotlinx.android.synthetic.main.activity_flat_ads_details.*

class AdsDetailsActivity : AppCompatActivity() {

    val imageList = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_details)

        var adId = intent?.getStringExtra("adId")

        addetails_homelord_pic.setOnClickListener {
            goToHomelordProfile(adId)
        }

        tenantMsgToOther_homelord_adDetails.setOnClickListener {
            sendMessageFromAdDetails(adId)
        }

        tenantCallToOther_homelord_adDetails.setOnClickListener {
            val callNumber = findViewById<TextView>(R.id.addetails_homelord_phone)

            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + callNumber.text.toString())
            startActivity(dialIntent)
        }

        
        retribeFlatData(adId)

    }

    private fun sendMessageFromAdDetails(adId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Ads").child(adId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var ads = snapshot.getValue(Ad::class.java)

                    var intent = Intent(this@AdsDetailsActivity, TenantMsgSendActivity::class.java)
                    intent.putExtra("myHomeLordId", ads!!.getHomeLordId())
                    startActivity(intent)
                }
            }
        })
    }


    private fun goToHomelordProfile(adId: String?) {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Ads").child(adId!!)
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var adItem = snapshot.getValue(Ad::class.java)

                    var intent = Intent(this@AdsDetailsActivity, HomeLordProfileActivity::class.java)
                    intent.putExtra("holeLordId", adItem!!.getHomeLordId())
                    startActivity(intent)
                }
            }
        })
    }

    private fun retribeFlatData(adId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Ads").child(adId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val ad = snapshot.getValue(Ad::class.java)

                    flatDetails_title.text = ad!!.getAdTitle()

                    if(ad.getGenerator() == "NO"){
                        generator_service.setTextColor(Color.parseColor("#FFF88787"))
                    }

                    if(ad.getLift() == "NO"){
                        lift_service.setTextColor(Color.parseColor("#FFF88787"))
                    }

                    if(ad.getSecurity() == "NO"){
                        security_service.setTextColor(Color.parseColor("#FFF88787"))
                    }

                    if(ad.getGas() == "NO"){
                        gas_service.setTextColor(Color.parseColor("#FFF88787"))
                    }

                    generator_service.text = ad!!.getGenerator()
                    lift_service.text = ad!!.getLift()
                    security_service.text = ad!!.getSecurity()
                    gas_service.text = ad!!.getGas()
                    flat_about_details.text = ad!!.getAdBoutHome()
                    ad_details_flatName.text = ad!!.getFlatName()
                    d_rent_month.text = ad!!.getRentForMonth()
                    d_total_room.text =  "Total rooms "+ad!!.getTotalRooms()
                    d_total_bath.text =  "Total baths"+ad!!.getTotalBaths()
                    d_attached_bath.text = "Attached bath"+ad!!.getAttachedBath()
                    d_eletricity_bill.text = "Electricity - "+ad!!.getElectricityBill()
                    d_water_bill.text = "Water - "+ad!!.getWaterBill()
                    d_gas_bill.text = "Gas - "+ad!!.getGasBill()
                    d_service_bill.text = "Maintanence - "+ad!!.getMaintanenceBill()


                    getPropertyIdBYFlatId(ad.getFlatId())
                    getFlatAdsImages(ad.getFlatId())

                }
            }
        })
    }

    private fun getFlatAdsImages(flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Flats").child(flatId)
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flatItem = snapshot.getValue(Flat::class.java)

                    getAllFlatImageStorage(flatItem!!.getFlatName())
                }
            }
        } )
    }

    private fun getAllFlatImageStorage(flatName: String) {
        var storageRef = FirebaseStorage.getInstance().reference.child(flatName)
        storageRef.listAll().addOnSuccessListener( OnSuccessListener { result ->
            result.items.forEach{storageRef ->
                getAdsImagesForSlide(flatName, storageRef.name)
            }
        })
    }

    private fun getAdsImagesForSlide(flatName: String, fileName: String) {
        var flatRef = FirebaseStorage.getInstance().reference.child(flatName).child(fileName)
        flatRef.downloadUrl.addOnCompleteListener { task ->
            imageList.add(SlideModel(task.result.toString()))

            val slideImgView = findViewById<ImageSlider>(R.id.flatAds_image_slider)
            slideImgView.setImageList(imageList, ScaleTypes.FIT)
            slideImgView.startSliding(4000)
        }
    }


    private fun getPropertyIdBYFlatId(flatId: String) {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (flat in snapshot.children){
                        var flatItem = flat.getValue(Flat::class.java)
                        if(flatItem!!.getFlatId().equals(flatId)){
                            getFlatAddressFromProperty(flatItem!!.getPropertyId())
                        }
                    }
                }
            }
        } )
    }


    private fun getFlatAddressFromProperty(propertyId: String) {
        var propertyRef = FirebaseDatabase.getInstance().getReference().child("Properties").child(propertyId)
        propertyRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                   var propertyItem = snapshot.getValue(Property::class.java)

                   pp_roadHouse_address.text = "road# "+propertyItem!!.getRoad()+", "+" house# "+ propertyItem!!.getHouse()
                   pp_address.text = propertyItem!!.getAddress()
                   pp_section.text = "section: "+propertyItem!!.getSection()
                   pp_thana.text = "thana: "+propertyItem!!.getThana()
                   pp_city.text = "city: "+propertyItem!!.getCity()
                   pp_division.text = "division: "+propertyItem!!.getDivision()

                    getFlatHomeloardData(propertyItem.getHomeLordId())
                }
            }
        } )
    }


    private fun getFlatHomeloardData(homeLordId: String) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Users").child(homeLordId)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)

                    addetails_homelord_name.text = user!!.getName()
                    Picasso.get().load(user.getImage()).into(addetails_homelord_pic)

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
                            addetails_homelord_phone.text = userDetails.getPhone()
                        }
                    }

                }
            }
        })
    }






}