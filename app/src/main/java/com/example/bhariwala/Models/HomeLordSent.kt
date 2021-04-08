package com.example.bhariwala.Models

class HomeLordSent {
    private var msgId = ""
    private var homeLordId = ""
    private var propertyId = ""
    private var flatId = ""
    private var message = ""
    private var date = ""
    private var time = ""

    constructor()
    constructor(msgId: String, homeLordId: String, propertyId: String, flatId: String, message: String, date:String, time:String) {
        this.msgId = msgId
        this.homeLordId = homeLordId
        this.propertyId = propertyId
        this.flatId = flatId
        this.message = message
        this.date = date
        this.time = time
    }

    fun getMsgId(): String {
        return msgId
    }
    fun setMsgId(msgId: String) {
        this.msgId = msgId
    }

    fun getHomeLordId(): String {
        return homeLordId
    }
    fun setHomeLordId(homeLordId: String) {
        this.homeLordId = homeLordId
    }

    fun getPropertyId(): String {
        return propertyId
    }
    fun setPropertyId(propertyId: String) {
        this.propertyId = propertyId
    }

    fun getFlatId(): String {
        return flatId
    }
    fun setFlatId(flatId: String) {
        this.flatId = flatId
    }

    fun getMessage(): String {
        return message
    }
    fun setMessage(message: String) {
        this.message = message
    }


    fun getDate(): String {
        return date
    }
    fun setDate(date: String) {
        this.date = date
    }

    fun getTime(): String {
        return time
    }
    fun setTime(time: String) {
        this.time = time
    }


}