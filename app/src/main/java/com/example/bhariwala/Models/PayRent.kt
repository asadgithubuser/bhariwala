package com.example.bhariwala.Models

class PayRent {
    private var payId = ""
    private var tenantId = ""
    private var homeLordId = ""
    private var homeLordName = ""
    private var time = ""
    private var date = ""
    private var isPending = ""
    private var flatName = ""
    private var buildingName = ""
    private var paidRentAmount = ""
    private var paidRentMonth = ""
    private var payViaService = ""

    constructor()
    constructor(payId: String, tenantId: String, homeLordId: String, homeLordName: String, time: String, date: String, isPending: String, flatName: String, buildingName: String, paidRentAmount: String, paidRentMonth: String, payViaService: String) {
        this.payId = payId
        this.tenantId = tenantId
        this.homeLordId = homeLordId
        this.homeLordName = homeLordName
        this.time = time
        this.date = date
        this.isPending = isPending
        this.flatName = flatName
        this.buildingName = buildingName
        this.paidRentAmount = paidRentAmount
        this.paidRentMonth = paidRentMonth
        this.payViaService = payViaService
    }


    fun getPayId():String{
        return payId
    }
    fun setPayId( payId: String){
        this.payId = payId
    }

    fun getTenantId():String{
        return tenantId
    }
    fun setTenantId(tenantId : String){
        this.tenantId = tenantId
    }

    fun getHomeLordId():String{
        return homeLordId
    }
    fun setHomeLordId( homeLordId: String){
        this.homeLordId = homeLordId
    }

    fun getHomeLordName():String{
        return homeLordName
    }
    fun setHomeLordName( homeLordName: String){
        this.homeLordName = homeLordName
    }

    fun getTime():String{
        return time
    }
    fun setTime(time : String){
        this.time = time
    }
    fun getDate():String{
        return date
    }
    fun setDate(date : String){
        this.date = date
    }


    fun getIsPending():String{
        return isPending
    }
    fun setIsPending(isPending : String){
        this.isPending = isPending
    }

    fun getFlatName():String{
        return flatName
    }
    fun setFlatName(flatName : String){
        this.flatName = flatName
    }

    fun getBuildingName():String{
        return buildingName
    }
    fun setBuildingName(buildingName : String){
        this.buildingName =buildingName
    }

    fun getPaidRentAmount():String{
        return paidRentAmount
    }
    fun setPaidRentAmount( paidRentAmount: String){
        this.paidRentAmount = paidRentAmount
    }

    fun getPaidRentMonth():String{
        return paidRentMonth
    }
    fun setPaidRentMonth( paidRentMonth: String){
        this.paidRentMonth =paidRentMonth
    }

    fun getPayViaService():String{
        return payViaService
    }
    fun setPayViaService( payViaService: String){
        this.payViaService =payViaService
    }



}