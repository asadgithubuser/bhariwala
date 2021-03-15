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
import com.example.bhariwala.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.drawer_menu_header.*
import kotlinx.android.synthetic.main.drawer_menu_header.view.*
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

    private var firebaseUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home_lord, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        view.tenant_details_btn.setOnClickListener{
            var intent = Intent(context, TenantActivity::class.java)
            startActivity(intent)
        }
        view.hl_message_cardView.setOnClickListener{
            var intent = Intent(context, MessageMainAcitivity::class.java)
            startActivity(intent)
        }


        view.properties_area_btn.setOnClickListener{
            var intent = Intent(context, PropertyAcitivity::class.java)
            startActivity(intent)
        }
        view.parcel_btn.setOnClickListener {
            startActivity(Intent(context, AddFlatActivity::class.java))
        }
        view.homelord_message.setOnClickListener {
            startActivity(Intent(context, MessgeAcitivity::class.java))
        }
        view.homelord_bill.setOnClickListener {
            startActivity(Intent(context, BillsActivity::class.java))
        }
        view.homelord_serviceman.setOnClickListener {
            startActivity(Intent(context, ServiceManActivity::class.java))
        }
        view.homelord_car_parking.setOnClickListener {
            startActivity(Intent(context, CarParkingActivity::class.java))
        }

        showUserInformation(view)


        return view
    }

    private fun showUserInformation(view: View) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                    view.hl_user_name.text = user!!.getName()
                    view.hl_user_status.text = user!!.getUser()
                    Picasso.get().load(user!!.getImage()).into(view.hl_user_image)
                }
            }
        })
    }

}