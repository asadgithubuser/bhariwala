package com.example.bhariwala.Models

class Expense {
    private var expenseId = ""
    private var note = ""
    private var amount = ""
    private var type = ""
    private var userId = ""
    private var time = ""
    private var date = ""
    private var expenseAM = ""

    constructor()
    constructor(expenseId: String, note: String, amount: String, type: String, userId: String, time: String, date: String, expenseAM: String) {
        this.expenseId = expenseId
        this.note = note
        this.amount = amount
        this.type = type
        this.userId = userId
        this.time = time
        this.date = date
        this.expenseAM = expenseAM
    }

    fun getExpenseId():String{
        return expenseId
    }
    fun setExpenseId(expenseId :String){
        this.expenseId = expenseId
    }

    fun getNote():String{
        return note
    }
    fun setNote(note :String){
        this.note = note
    }

    fun getAmount():String{
        return amount
    }
    fun setAmount(amount :String){
        this.amount = amount
    }

    fun getType():String{
        return type
    }
    fun setType(type :String){
        this.type = type
    }

    fun getUserId():String{
        return userId
    }
    fun setUserId(userId :String){
        this.userId = userId
    }

    fun getTime():String{
        return time
    }
    fun setTime( time:String){
        this.time = time
    }

    fun getDate():String{
        return date
    }
    fun setDate(date :String){
        this.date = date
    }

    fun getExpenseAM():String{
        return expenseAM
    }
    fun setExpenseAM(expenseAM :String){
        this.expenseAM = expenseAM
    }




}