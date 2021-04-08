package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.CarParkingAdapter
import com.example.bhariwala.Models.CarPark
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_car_parking_list.*

class CarParkingListActivity : AppCompatActivity() {
    private var carParkList: MutableList<CarPark>? = null
    private var carParkAdapter: CarParkingAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_parking_list)

        var actionBr = getSupportActionBar()
        if(actionBr != null){
            actionBr.setTitle("Parking Car History")
            actionBr.setDisplayHomeAsUpEnabled(true)
        }

        var parkingRecyclerView = findViewById<RecyclerView>(R.id.carParking_List_recyclerView)
        carParkList = ArrayList()
        parkingRecyclerView.setHasFixedSize(true)
        parkingRecyclerView.layoutManager = LinearLayoutManager(this)

        carParkAdapter = CarParkingAdapter(this, carParkList as MutableList<CarPark>)
        parkingRecyclerView.adapter = carParkAdapter


        carParking_adding_fab_btn.setOnClickListener {
            var intent = Intent(this, AddNewParkingCarActivity::class.java)
            startActivity(intent)
        }


       // getAllParkingCarList()

    }

    private fun getAllParkingCarList() {
        var ref = FirebaseDatabase.getInstance().reference.child("ParkingCars")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    carParkList!!.clear()
                    for(item in snapshot.children){
                        var parkingcar = item.getValue(CarPark::class.java)
                        carParkList!!.add(parkingcar!!)
                    }
                    carParkAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }


}