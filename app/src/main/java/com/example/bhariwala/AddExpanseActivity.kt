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
import kotlinx.android.synthetic.main.activity_add_expanse.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AddExpanseActivity : AppCompatActivity() {
    var seletDepoType = ""
    var selectAfterMind = ""
    var time = ""
    var date = ""
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expanse)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("Add Expanse")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        var ttim = LocalTime.now()
        time = ttim.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

        var ddate = LocalDate.now()
        date = ddate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))


        var expense_shopping = findViewById<ThemedButton>(R.id.add_expense_shopping)
        var expense_travel = findViewById<ThemedButton>(R.id.add_expense_travel)
        var expense_food = findViewById<ThemedButton>(R.id.add_expense_food)
        var expense_dhar = findViewById<ThemedButton>(R.id.add_expense_dhar)
        var expense_lose = findViewById<ThemedButton>(R.id.add_expense_lose)
        var expense_familyCost = findViewById<ThemedButton>(R.id.add_expense_familyCost)
        var expense_addaCost = findViewById<ThemedButton>(R.id.add_expense_addaCost)

        expenseTypes_btn_groups.setOnSelectListener { button: ThemedButton ->
            when(button){
                expense_shopping -> { seletDepoType = button.text}
                expense_travel -> { seletDepoType = button.text}
                expense_food -> { seletDepoType = button.text}
                expense_dhar -> { seletDepoType = button.text}
                expense_lose -> { seletDepoType = button.text}
                expense_familyCost -> { seletDepoType = button.text}
                expense_addaCost -> { seletDepoType = button.text}
            }
        }


        add_expense_afterMind.setOnSelectListener { buttonId: ThemedButton ->
            when(buttonId)
            {
                findViewById<ThemedButton>(R.id.add_expense_important) -> { selectAfterMind = buttonId.text}
                findViewById<ThemedButton>(R.id.add_expense_some_important) -> { selectAfterMind = buttonId.text}
                findViewById<ThemedButton>(R.id.add_expense_valueless) -> { selectAfterMind = buttonId.text}
            }
        }

        addExpense_addBtn.setOnClickListener {
            saveExpenseItem()
        }
    }

    fun showToast(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun saveExpenseItem() {
        var note = findViewById<EditText>(R.id.add_expense_note)
        var amount = findViewById<TextInputEditText>(R.id.add_expense_amount)

        when{
            TextUtils.isEmpty(seletDepoType) -> showToast("Expense Type Can not be null")
            TextUtils.isEmpty(note.text.toString()) -> showToast("Note Can not be null")
            TextUtils.isEmpty(amount.text.toString()) -> showToast("Amount Can not be null")
            TextUtils.isEmpty(selectAfterMind) -> showToast("Expense After Mind Can not be null")

            else -> {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Add New Ad")
                progressDialog.setMessage("Please wait, we are take sometimes for adding to expense. ")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                var firebaseRef = FirebaseDatabase.getInstance().reference.child("Expenses").child(firebaseUser!!.uid)
                var depositMap = HashMap<String, Any>()

                var idd = firebaseRef.push().key
                depositMap["expenseId"] = idd!!
                depositMap["note"] = note.text.toString()
                depositMap["amount"] = amount.text.toString()
                depositMap["type"] = seletDepoType
                depositMap["userId"] = firebaseUser!!.uid
                depositMap["time"] = time
                depositMap["date"] = date
                depositMap["expenseAM"] = selectAfterMind

                firebaseRef.child(idd).setValue(depositMap).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        progressDialog.dismiss()
                        showToast("New expense added successfully")
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