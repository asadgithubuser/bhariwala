package com.example.bhariwala.Models

class User {
    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var profession: String = ""
    private var image: String = ""
    private var uid: String = ""
    private var user: String = ""
//    private var status: String = ""

    constructor()
    constructor(name: String, email: String, password: String, profession: String, image:String, uid:String, user: String){
        this.name = name
        this.email = email
        this.password = password
        this.profession = profession
        this.image = image
        this.uid = uid
        this.user = user
    }

    fun getName():String{
        return name
    }
    fun getEmail():String{
        return email
    }
    fun getPassword():String{
        return password
    }
    fun getProfession():String{
        return profession
    }
    fun getImage():String{
        return image
    }
    fun getUid():String{
        return uid
    }
    fun getUser():String{
        return user
    }

    fun setName(name: String){
        this.name = name
    }
    fun setEmail(email: String){
        this.email = email
    }
    fun setPassword(password: String){
        this.password = password
    }
    fun setProfession(profession: String){
        this.profession = profession
    }
    fun setImage(image: String){
        this.image = image
    }
    fun setUid(uid: String){
        this.uid = uid
    }
    fun setUser(user: String){
        this.user = user
    }


//    fun getStatus(): String{
//        return status
//    }
//
//    fun setStatus(status: String){
//        this.status = status
//    }



}