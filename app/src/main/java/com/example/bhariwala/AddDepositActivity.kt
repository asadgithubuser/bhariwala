package com.example.bhariwala

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_deposit.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.HashMap

class AddDepositActivity : AppCompatActivity() {

    var seletDepoType = ""
    var time = ""
    var date = ""
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_deposit)

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add Deposit")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser


        var ttim = LocalTime.now()
        time = ttim.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

        var ddate = LocalDate.now()
        date = ddate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))





        var profit = findViewById<ThemedButton>(R.id.add_depo_businessProfit)
        var salary = findViewById<ThemedButton>(R.id.add_depo_salary)
        var dhar = findViewById<ThemedButton>(R.id.add_depo_dhar)
        var loan = findViewById<ThemedButton>(R.id.add_depo_loan)
        var korzza = findViewById<ThemedButton>(R.id.add_depo_korzza)

        add_deposit_types_groupbtns.setOnSelectListener { button: ThemedButton ->
            when(button){
                 profit -> { seletDepoType = button.text}
                 salary -> { seletDepoType = button.text}
                 dhar -> { seletDepoType = button.text}
                 loan -> { seletDepoType = button.text}
                 korzza -> { seletDepoType = button.text}
            }
        }


        add_deposit_addBtn.setOnClickListener {
            saveDepositItem()
        }

    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun saveDepositItem() {
        var note = findViewById<EditText>(R.id.add_deposit_note)
        var amount = findViewById<TextInputEditText>(R.id.add_deposit_amount)

        when{
            TextUtils.isEmpty(seletDepoType) -> showToast("Deposit Type Can not be null")
            TextUtils.isEmpty(note.text.toString()) -> showToast("Note Can not be null")
            TextUtils.isEmpty(amount.text.toString()) -> showToast("Amount Can not be null")

            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Add New Ad")
                progressDialog.setMessage("Please wait, we are take sometimes for adding to deposit. ")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var firebaseRef = FirebaseDatabase.getInstance().reference.child("Deposits").child(firebaseUser!!.uid)
                var depositMap = HashMap<String, Any>()

                var idd = firebaseRef.push().key
                depositMap["depoId"] = idd!!
                depositMap["note"] = note.text.toString()
                depositMap["amount"] = amount.text.toString()
                depositMap["type"] = seletDepoType
                depositMap["userId"] = firebaseUser!!.uid
                depositMap["time"] = time
                depositMap["date"] = date

                firebaseRef.child(idd).setValue(depositMap).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        progressDialog.dismiss()
                        showToast("New Deposit added successfully")
                        finish()
                    }else{
                        progressDialog.dismiss()
                        showToast("Error: "+task.exception.toString())
                    }
                }

            }


        }

    }


}