package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.Deposit
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class DepositAdapter(private val mContext: Context, private val depositList: List<Deposit>)
    : RecyclerView.Adapter<DepositAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepositAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.deposit_layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return depositList.size
    }

    override fun onBindViewHolder(holder: DepositAdapter.ViewHolder, position: Int) {
        var deposit = depositList[position]

        holder.deposit_type_name.text = deposit.getType()
        holder.deposit_date.text = deposit.getDate()
        holder.deposit_time.text = deposit.getTime()
        holder.deposit_note.text = deposit.getNote()
        holder.deposit_amount.text = deposit.getAmount()+" tk"

        firebaseUser = FirebaseAuth.getInstance().currentUser

        holder.deposit_optionDots.setOnClickListener {
            val popupMenu = PopupMenu(mContext, holder.deposit_optionDots)
            popupMenu.menuInflater.inflate(R.menu.deposit_expense_option_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.update_item ->
                        Toast.makeText(mContext, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.hide_item ->
                        Toast.makeText(mContext, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.delete_item -> { deleteDepositItem(deposit.getDepoId()) }

                }
                true
            })
            popupMenu.show()
        }

    }

    private fun deleteDepositItem(depoId: String) {
        var depositRef = FirebaseDatabase.getInstance().reference.child("Deposits").child(firebaseUser!!.uid)

        depositRef.child(depoId).removeValue().addOnCompleteListener {task ->
            if(task.isSuccessful){
                Toast.makeText(mContext, "Deposit item has been deleted successfully.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(mContext, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }


    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deposit_type_name : TextView = itemView.findViewById(R.id.expense_type_name)
        var deposit_date : TextView = itemView.findViewById(R.id.expense_date)
        var deposit_time : TextView = itemView.findViewById(R.id.expense_time)
        var deposit_note : TextView = itemView.findViewById(R.id.expense_note)
        var deposit_optionDots : ImageView = itemView.findViewById(R.id.expense_optionDots)
        var deposit_amount : TextView = itemView.findViewById(R.id.expense_amount)
    }
}