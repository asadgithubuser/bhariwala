package com.example.bhariwala.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bhariwala.*
import kotlinx.android.synthetic.main.fragment_home_lord.*
import kotlinx.android.synthetic.main.fragment_home_lord.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeLordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeLordFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home_lord, container, false)

        Toast.makeText(context, "clicked on home", Toast.LENGTH_LONG).show()

        view.tenant_details_btn.setOnClickListener{
            Log.d("id2", "fdlsflds")
            Toast.makeText(context, "clicked on home", Toast.LENGTH_LONG).show()

            var intent = Intent(context, TenantActivity::class.java)
            startActivity(intent)
        }
//        properties_area_btn.setOnClickListener{
//            var intent = Intent(this, PropertyAcitivity::class.java)
//            startActivity(intent)
//        }
//        parcel_btn.setOnClickListener {
//            startActivity(Intent(this, AddFlatActivity::class.java))
//        }
//        homelord_message.setOnClickListener {
//            startActivity(Intent(this, MessgeAcitivity::class.java))
//        }
//        homelord_bill.setOnClickListener {
//            startActivity(Intent(this, BillsActivity::class.java))
//        }
//        homelord_serviceman.setOnClickListener {
//            startActivity(Intent(this, ServiceManActivity::class.java))
//        }
//        homelord_car_parking.setOnClickListener {
//            startActivity(Intent(this, CarParkingActivity::class.java))
//        }



        return view
    }

}