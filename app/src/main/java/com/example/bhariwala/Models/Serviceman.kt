package com.example.bhariwala.Models

class Serviceman {
    private var servicemanId = ""
    private var homelordId = ""
    private var servicemnName = ""
    private var servicemnPhone = ""
    private var servicemnJoinDate = ""
    private var servicemnAge = ""
    private var servicemnSalary = ""
    private var servicemnFullAddress = ""
    private var selectedSrvcmnType = ""
    private var selectedServicemnDuty = ""

    constructor()
    constructor(servicemanId: String, homelordId: String, servicemnName: String, servicemnPhone: String, servicemnJoinDate: String, servicemnAge: String, servicemnSalary: String, servicemnFullAddress: String, selectedSrvcmnType: String, selectedServicemnDuty: String) {
        this.servicemanId = servicemanId
        this.homelordId = homelordId
        this.servicemnName = servicemnName
        this.servicemnPhone = servicemnPhone
        this.servicemnJoinDate = servicemnJoinDate
        this.servicemnAge = servicemnAge
        this.servicemnSalary = servicemnSalary
        this.servicemnFullAddress = servicemnFullAddress
        this.selectedSrvcmnType = selectedSrvcmnType
        this.selectedServicemnDuty = selectedServicemnDuty
    }


    fun getServicemanId():String{
        return servicemanId
    }
    fun setServicemanId(servicemanId: String){
        this.servicemanId = servicemanId
    }
    fun getHomelordId():String{
        return homelordId
    }
    fun setHomelordId( homelordId: String){
        this.homelordId = homelordId
    }
    fun getServicemnName():String{
        return servicemnName
    }
    fun setServicemnName(servicemnName : String){
        this.servicemnName = servicemnName
    }

    fun getServicemnPhone():String{
        return servicemnPhone
    }
    fun setServicemnPhone(servicemnPhone : String){
        this.servicemnPhone = servicemnPhone
    }

    fun getServicemnJoinDate():String{
        return servicemnJoinDate
    }
    fun setServicemnJoinDate(servicemnJoinDate : String){
        this.servicemnJoinDate = servicemnJoinDate
    }






    fun getServicemnAge():String{
        return servicemnAge
    }
    fun setServicemnAge( servicemnAge: String){
        this.servicemnAge = servicemnAge
    }
    fun getServicemnSalary():String{
        return servicemnSalary
    }
    fun setServicemnSalary(servicemnSalary : String){
        this.servicemnSalary = servicemnSalary
    }
    fun getServicemnFullAddress():String{
        return servicemnFullAddress
    }
    fun setServicemnFullAddress( servicemnFullAddress: String){
        this.servicemnFullAddress = servicemnFullAddress
    }
    fun getSelectedSrvcmnType():String{
        return selectedSrvcmnType
    }
    fun setSelectedSrvcmnType( selectedSrvcmnType: String){
        this.selectedSrvcmnType = selectedSrvcmnType
    }
    fun getSelectedServicemnDuty():String{
        return selectedServicemnDuty
    }
    fun setSelectedServicemnDuty( selectedServicemnDuty: String){
        this.selectedServicemnDuty = selectedServicemnDuty
    }


}