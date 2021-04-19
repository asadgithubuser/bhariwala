package com.example.bhariwala.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bhariwala.*
import com.example.bhariwala.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home_lord.view.*
import kotlinx.android.synthetic.main.homelord_required_info_dialog.view.*

class HomeLordFragment : Fragment() {

    private var firebaseUser: FirebaseUser? = null
    private var userStatus : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home_lord, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        view.all_tenants_for_homelord.setOnClickListener{
            var intent = Intent(context, HomeLordTenantsActivity::class.java)
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
           // startActivity(Intent(context, AddFlatActivity::class.java))
        }
        view.homelord_message.setOnClickListener {
            startActivity(Intent(context, MessageMainAcitivity::class.java))
        }
        view.homelord_bill.setOnClickListener {
            var intent = Intent(context, HomeLordBillTenantActivity::class.java)
            intent.putExtra("currentUserStatus", userStatus)
            startActivity(intent)
        }
        view.homelord_serviceman.setOnClickListener {
            startActivity(Intent(context, ServiceManActivity::class.java))
        }
        view.homelord_car_parking.setOnClickListener {
            startActivity(Intent(context, CarParkingActivity::class.java))
        }

//        view.addServicemanToHomelord.setOnClickListener {
//            var intent = Intent(context, AssignServicemanToHomelordActivity::class.java)
//            startActivity(intent)
//        }

        showUserInformation(view)

        //showRequiredAccUpdateDialog()

        return view
    }

    private fun showRequiredAccUpdateDialog() {
        var mDialogView = LayoutInflater.from(context).inflate(R.layout.homelord_required_info_dialog, null)

        val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("Update Form")
                .setCancelable(false)
        var mAlertDialog = mBuilder.show()


        mDialogView.dialog_UpdateAcc_updateBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogView.dialog_UpdateAcc_CancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }




    }

    private fun showUserInformation(view: View) {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                         userStatus = user!!.getUser()
                        view.hl_user_name.text = user!!.getName()
                        view.hl_user_status.text = user!!.getUser()
                        Picasso.get().load(user!!.getImage()).into(view.hl_user_image)
                }
            }
        })
    }

}