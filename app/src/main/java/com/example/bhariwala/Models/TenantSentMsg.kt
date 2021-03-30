package com.example.bhariwala.Models

class TenantSentMsg {
    private var tsmId = ""
    private var homeLrdId = ""
    private var tenatId = ""
    private var homeLrdName = ""
    private var msgSubject = ""
    private var msgText = ""
    private var date = ""
    private var time = ""

    constructor()
    constructor(tsmId: String, homeLrdId: String, tenatId: String, homeLrdName: String, msgSubject: String, msgText: String, date: String, time: String) {
        this.tsmId = tsmId
        this.homeLrdId = homeLrdId
        this.tenatId = tenatId
        this.homeLrdName = homeLrdName
        this.msgSubject = msgSubject
        this.msgText = msgText
        this.date = msgText
        this.time = msgText
    }

    fun getTsmId(): String {
        return tsmId
    }
    fun setTsmId(tsmId: String) {
        this.tsmId = tsmId
    }

    fun getHomeLrdId(): String {
        return homeLrdId
    }
    fun setHomeLrdId(homeLrdId: String) {
        this.homeLrdId = homeLrdId
    }

    fun getTenatId(): String {
        return tenatId
    }
    fun setTenatId(tenatId: String) {
        this.tenatId = tenatId
    }

    fun getHomeLrdName(): String {
        return homeLrdName
    }
    fun setHomeLrdName(homeLrdName: String) {
        this.homeLrdName = homeLrdName
    }

    fun getMsgSubject(): String {
        return msgSubject
    }
    fun setMsgSubject(msgSubject: String) {
        this.msgSubject = msgSubject
    }

    fun getMsgText(): String {
        return msgText
    }
    fun setMsgText(msgText: String) {
        this.msgText = msgText
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