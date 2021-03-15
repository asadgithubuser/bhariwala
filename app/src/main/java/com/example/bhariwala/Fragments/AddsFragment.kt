package com.example.bhariwala.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Adapter.AddsAdapter
import com.example.bhariwala.AddFlatActivity
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_flat.*
import kotlinx.android.synthetic.main.activity_add_flat.view.*
import kotlinx.android.synthetic.main.fragment_adds.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [AddsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adsAdapter: AddsAdapter? = null
    private var mAdsList: MutableList<Flat>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_adds, container, false)


        view.ads_Addflat_fab_btn.setOnClickListener {
            startActivity(Intent(context, AddFlatActivity::class.java))
        }




//        /// =============== adds fragment operations =============

        recyclerView = view.findViewById(R.id.ads_list_recyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        mAdsList = ArrayList()
        adsAdapter = context?.let{AddsAdapter(it, mAdsList as ArrayList<Flat>)}
        recyclerView?.adapter = adsAdapter


        retribeAllAds()

        return view
    }

    private fun retribeAllAds() {
        var adsRef = FirebaseDatabase.getInstance().getReference().child("Flats")
        adsRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (ads in snapshot.children){
                        var adsItem = ads.getValue(Flat::class.java)
                            mAdsList?.add(adsItem!!)
                    }
                    adsAdapter?.notifyDataSetChanged()
                }
            }
        } )
    }


}