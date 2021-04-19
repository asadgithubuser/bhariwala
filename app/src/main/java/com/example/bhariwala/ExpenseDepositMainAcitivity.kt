package com.example.bhariwala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.bhariwala.Fragments.ExpenseDepositFragment
import com.example.bhariwala.Fragments.UserDepositFragment
import com.example.bhariwala.Fragments.UserExpenseFragment
import kotlinx.android.synthetic.main.activity_expense_deposit_main.*

class ExpenseDepositMainAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_deposit_main)

        var actinnBar = getSupportActionBar()
        if(actinnBar != null){
            actinnBar.setTitle("Expense & Deposit")
            actinnBar.setHomeAsUpIndicator(R.drawable.back_icon)
            actinnBar.setDisplayHomeAsUpEnabled(true)
        }


        var exDpAdapter = ExpenseDepositFragment(supportFragmentManager)
        exDpAdapter.addFragment(UserDepositFragment(), "Deposit")
        exDpAdapter.addFragment(UserExpenseFragment(), "Expense")


        exdp_main_ViewPager.adapter = exDpAdapter
        exdp_appBar_tabs.setupWithViewPager(exdp_main_ViewPager)


    }
}