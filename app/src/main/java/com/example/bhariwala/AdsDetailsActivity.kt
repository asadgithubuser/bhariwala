package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bhariwala.Models.Ad
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Property
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ads_details.*

class AdsDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_details)

        var adId = intent?.getStringExtra("adId")

        retribeFlatData(adId)

    }

    private fun retribeFlatData(adId: String?) {
        var flatRef = FirebaseDatabase.getInstance().reference.child("Ads").child(adId!!)
        flatRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val ad = snapshot.getValue(Ad::class.java)

                    getPropertyIdBYFlatId(ad!!.getFlatId())



                    flatDetails_title.text = ad!!.getAdTitle()
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


                }
            }
        })
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
//
//                   property_address = propertyItem!!.getAddress()
//                   property_road = propertyItem!!.getRoad()
//                   property_house = propertyItem!!.getHouse()
//                   property_section = propertyItem!!.getSection()
//                   property_city = propertyItem!!.getCity()
//                   property_thana = propertyItem!!.getThana()
//                   property_division = propertyItem!!.getDivision()


                   pp_roadHouse_address.text = "road# "+propertyItem!!.getRoad()+", "+" house# "+ propertyItem!!.getHouse()
                   pp_address.text = propertyItem!!.getAddress()
                   pp_section.text = "section: "+propertyItem!!.getSection()
                   pp_thana.text = "thana: "+propertyItem!!.getThana()
                   pp_city.text = "city: "+propertyItem!!.getCity()
                   pp_division.text = "division: "+propertyItem!!.getDivision()

//
//
//                pp_roadHouse_address.text = "road# "+property_road+" house# "+ property_house
//                pp_address.text = property_address
//                pp_section.text = property_section
//                pp_thana.text = property_thana
//                pp_city.text = property_city
//                pp_division.text = property_division




                }
            }
        } )
    }







}