package com.example.bhariwala.Models

class Tenant {
    private var tenantId = ""
    private var tenantUserName = ""
    private var homeLordId = ""
    private var flatId = ""
    private var propertyId = ""
    private var date = ""

    constructor()
    constructor(tenantId: String, tenantUserName: String, homeLordId: String, flatId: String, propertyId: String, date: String) {
        this.tenantId = tenantId
        this.tenantUserName = tenantUserName
        this.homeLordId = homeLordId
        this.flatId = flatId
        this.propertyId = propertyId
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

    fun getFlatId():String{
        return flatId
    }
    fun setFlatId(flatId :String){
        this.flatId = flatId
    }

    fun getPropertyId():String{
        return propertyId
    }
    fun setPropertyId( propertyId: String){
        this.propertyId = propertyId
    }

    fun getDate():String{
        return date
    }
    fun setDate( date: String){
        this.date = date
    }

}