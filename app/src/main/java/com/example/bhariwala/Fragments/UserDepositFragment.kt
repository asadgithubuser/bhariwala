package com.example.bhariwala.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.DepositAdapter
import com.example.bhariwala.AddDepositActivity
import com.example.bhariwala.Models.Deposit
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_user_deposit.view.*

class UserDepositFragment : Fragment() {

    private var firebaseUser: FirebaseUser? = null
    private var depositAdapter : DepositAdapter? = null
    private var depositList: MutableList<Deposit>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user_deposit, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser


        depositList = ArrayList()
        var recyclerView = view.findViewById<RecyclerView>(R.id.depositList_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        depositAdapter = context?.let { DepositAdapter(it, depositList as MutableList<Deposit>) }
        recyclerView.adapter = depositAdapter


        view.fab_addUser_Deposit_btn.setOnClickListener {
            startActivity(Intent(context, AddDepositActivity()::class.java))
        }



        getAllDepositList()

      return view
    }

    private fun getAllDepositList() {
        var fRef = FirebaseDatabase.getInstance().reference.child("Deposits").child(firebaseUser!!.uid)
        fRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    depositList!!.clear()
                    for (item in snapshot.children){
                        var deposit = item.getValue(Deposit::class.java)
                        depositList!!.add(deposit!!)
                    }
                    depositAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }

}