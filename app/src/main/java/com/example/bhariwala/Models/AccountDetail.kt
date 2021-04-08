package com.example.bhariwala.Models

class AccountDetail {
    private var userId = ""
    private var phone = ""
    private var aboutUser = ""
    private var address = ""
    private var roadNo = ""
    private var houseNo = ""
    private var section = ""
    private var thana = ""
    private var postCode = ""
    private var city = ""
    private var divisions = ""


    constructor()
    constructor(
        userId: String,
        phone: String,
        aboutUser: String,
        address: String,
        roadNo: String,
        houseNo: String,
        section: String,
        thana: String,
        postCode: String,
        city: String,
        divisions: String
    ) {
        this.userId = userId
        this.phone = phone
        this.aboutUser = aboutUser
        this.address = address
        this.roadNo = roadNo
        this.houseNo = houseNo
        this.section = section
        this.thana = thana
        this.postCode = postCode
        this.city = city
        this.divisions = divisions
    }

    fun getUserId():String{
        return userId
    }
    fun setUserId( userId: String){
        this.userId =  userId
    }


    fun getPhone():String{
        return phone
    }
    fun setPhone(phone : String){
        this.phone = phone
    }


    fun getAboutUser():String{
        return aboutUser
    }
    fun setAboutUser(aboutUser : String){
        this.aboutUser = aboutUser
    }


    fun getAddress():String{
        return address
    }
    fun setAddress(address : String){
        this.address =address
    }


    fun getRoadNo():String{
        return roadNo
    }
    fun setRoadNo(roadNo : String){
        this.roadNo =roadNo
    }


    fun getHouseNo():String{
        return houseNo
    }
    fun setHouseNo( houseNo: String){
        this.houseNo = houseNo
    }


    fun getSection():String{
        return section
    }
    fun setSection(section : String){
        this.section = section
    }

    fun getThana():String{
        return thana
    }
    fun setThana(thana : String){
        this.thana = thana
    }


    fun getPostCode():String{
        return postCode
    }
    fun setPostCode(postCode : String){
        this.postCode =postCode
    }


    fun getCity():String{
        return city
    }
    fun setCity(city : String){
        this.city =city
    }


    fun getDivisions():String{
        return divisions
    }
    fun setDivisions(divisions : String){
        this.divisions = divisions
    }



}