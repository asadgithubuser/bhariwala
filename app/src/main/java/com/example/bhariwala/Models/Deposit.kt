package com.example.bhariwala.Models

class Deposit {
    private var depoId = ""
    private var note = ""
    private var amount = ""
    private var type = ""
    private var userId = ""
    private var time = ""
    private var date = ""

    constructor()
    constructor(depoId: String, note: String, amount: String, type: String, userId: String, time: String, date: String) {
        this.depoId = depoId
        this.note = note
        this.amount = amount
        this.type = type
        this.userId = userId
        this.time = time
        this.date = date
    }

    fun getDepoId():String{
        return depoId
    }

    fun setDepoId(depoId :String){
        this.depoId = depoId
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




}