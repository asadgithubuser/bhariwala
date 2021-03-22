package com.example.bhariwala.Models

class HomeLordSent {
    private var msgId = ""
    private var homeLordId = ""
    private var propertyName = ""
    private var flatName = ""
    private var message = ""
    private var date = ""
    private var time = ""

    constructor()
    constructor(msgId: String, homeLordId: String, propertyName: String, flatName: String, message: String, date:String, time:String) {
        this.msgId = msgId
        this.homeLordId = homeLordId
        this.propertyName = propertyName
        this.flatName = flatName
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

    fun getPropertyName(): String {
        return propertyName
    }
    fun setPropertyName(propertyName: String) {
        this.propertyName = propertyName
    }

    fun getFlatName(): String {
        return flatName
    }
    fun setFlatName(flatName: String) {
        this.flatName = flatName
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