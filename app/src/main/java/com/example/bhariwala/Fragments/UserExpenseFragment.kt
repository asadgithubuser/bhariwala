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
import com.example.bhariwala.Adapter.ExpenseAdapter
import com.example.bhariwala.AddExpanseActivity
import com.example.bhariwala.Models.Deposit
import com.example.bhariwala.Models.Expense
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_user_expense.view.*


class UserExpenseFragment : Fragment() {
    private var firebaseUser: FirebaseUser? = null
    private var expenseAdapter : ExpenseAdapter? = null
    private var expenseList: MutableList<Expense>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user_expense, container, false)

        view.fab_addUser_Expense_btn.setOnClickListener {
            startActivity(Intent(context, AddExpanseActivity()::class.java))
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser


        expenseList = ArrayList()
        var recyclerView = view.findViewById<RecyclerView>(R.id.expenseList_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        expenseAdapter = context?.let {ExpenseAdapter(it, expenseList as MutableList<Expense>) }
        recyclerView.adapter = expenseAdapter


        getAllExpenseList()


      return view
    }


    private fun getAllExpenseList() {
        var fRef = FirebaseDatabase.getInstance().reference.child("Expenses").child(firebaseUser!!.uid)
        fRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    expenseList!!.clear()
                    for (item in snapshot.children){
                        var expense = item.getValue(Expense::class.java)
                        expenseList!!.add(expense!!)
                    }
                    expenseAdapter!!.notifyDataSetChanged()
                }
            }

        })
    }


}