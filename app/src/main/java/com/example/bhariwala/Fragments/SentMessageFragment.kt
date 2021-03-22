package com.example.bhariwala.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bhariwala.Adapter.HomeLordSentAdapter
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.HomeLordSent
import com.example.bhariwala.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_sent_message.*
import kotlinx.android.synthetic.main.fragment_sent_message.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SentMessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SentMessageFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var homlordSentMsgList: MutableList<HomeLordSent>? = null
    private var hlSentAdapter : HomeLordSentAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sent_message, container, false)


        homlordSentMsgList = ArrayList()
        hlSentAdapter = context?.let { HomeLordSentAdapter(it, homlordSentMsgList as MutableList<HomeLordSent>) }
        view.homlord_sent_msg_recyclerView.setHasFixedSize(true)

        view.homlord_sent_msg_recyclerView.layoutManager = LinearLayoutManager(context)
        view.homlord_sent_msg_recyclerView.adapter = hlSentAdapter


        retrieveAllFlatName()
        return view
    }


    private fun retrieveAllFlatName() {
        var bulidingRef = FirebaseDatabase.getInstance().reference.child("HomeLordSentMsg")
        bulidingRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                    var message = item.getValue(HomeLordSent::class.java)
                        homlordSentMsgList?.add(message!!)
                    }
                    hlSentAdapter!!.notifyDataSetChanged()
                }
            }
        })
    }


}