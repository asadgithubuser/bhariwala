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
import com.example.bhariwala.Models.Expense
import com.example.bhariwala.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class ExpenseAdapter(private val mContext: Context, private val expenseList: List<Expense>)
    : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.deposit_layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ExpenseAdapter.ViewHolder, position: Int) {
        var expense = expenseList[position]

        holder.expense_type_name.text = expense.getType()
        holder.expense_date.text = expense.getDate()
        holder.expense_time.text = expense.getTime()
        holder.expense_note.text = expense.getNote()
        holder.expense_amount.text = expense.getAmount()+" tk"
        holder.expense_afterMind.text = expense.getExpenseAM()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        holder.expense_optionDots.setOnClickListener {
            val popupMenu = PopupMenu(mContext, holder.expense_optionDots)
            popupMenu.menuInflater.inflate(R.menu.deposit_expense_option_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.update_item ->
                        Toast.makeText(mContext, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.hide_item ->
                        Toast.makeText(mContext, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                    R.id.delete_item -> { deleteDepositItem(expense.getExpenseId()) }

                }
                true
            })
            popupMenu.show()
        }
    }



    private fun deleteDepositItem(expenseId: String) {
        var depositRef = FirebaseDatabase.getInstance().reference.child("Expenses").child(firebaseUser!!.uid)

        depositRef.child(expenseId).removeValue().addOnCompleteListener {task ->
            if(task.isSuccessful){
                Toast.makeText(mContext, "Expense item has been deleted successfully.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(mContext, "Error: "+ task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }








    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var expense_type_name : TextView = itemView.findViewById(R.id.expense_type_name)
        var expense_date : TextView = itemView.findViewById(R.id.expense_date)
        var expense_time : TextView = itemView.findViewById(R.id.expense_time)
        var expense_note : TextView = itemView.findViewById(R.id.expense_note)
        var expense_optionDots : ImageView = itemView.findViewById(R.id.expense_optionDots)
        var expense_amount : TextView = itemView.findViewById(R.id.expense_amount)
        var expense_afterMind : TextView = itemView.findViewById(R.id.expense_afterMind)
    }
}