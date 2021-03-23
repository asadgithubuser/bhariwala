package com.example.bhariwala.Models

class Tenant {
    private var tenantId = ""
    private var tenantUserName = ""
    private var homeLordId = ""
    private var flatName = ""
    private var propertyName = ""
    private var rent = ""
    private var date = ""

    constructor()
    constructor(tenantId: String, tenantUserName: String, homeLordId: String, flatName: String, propertyName: String, rent: String, date: String) {
        this.tenantId = tenantId
        this.tenantUserName = tenantUserName
        this.homeLordId = homeLordId
        this.flatName = flatName
        this.propertyName = propertyName
        this.rent = rent
        this.date = date
    }

    fun getTenantId():String{
        return tenantId
    }
    fun setTenantId( tenantId:String){
        this.tenantId = tenantId
    }

    fun getTenantUserName():String{
        return tenantUserName
    }
    fun setTenantUserName( tenantUserName:String){
        this.tenantUserName = tenantUserName
    }


    fun getHomeLordId():String{
        return homeLordId
    }
    fun setHomeLordId( homeLordId: String){
        this.homeLordId = homeLordId
    }


    fun getFlatName():String{
        return flatName
    }
    fun setFlatName(flatName :String){
        this.flatName = flatName
    }


    fun getPropertyName():String{
        return propertyName
    }
    fun setPropertyName( propertyName: String){
        this.propertyName = propertyName
    }

    fun getRent():String{
        return rent
    }
    fun setRent( rent: String){
        this.rent = rent
    }


    fun getDate():String{
        return date
    }
    fun setDate( date: String){
        this.date = date
    }






}