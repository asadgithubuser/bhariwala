package com.example.bhariwala.Models

class Ad {
    private var adId = ""
    private var adTitle = ""
    private var adBoutHome = ""
    private var homeType = ""
    private var flatName = ""
    private var rentForWhome = ""
    private var security = ""
    private var gas = ""
    private var lift = ""
    private var generator = ""
    private var religionName = ""
    private var homeLordId = ""
    private var flatId = ""
    private var squareFeet = ""
    private var persons = ""
    private var totalRooms = ""
    private var totalBaths = ""
    private var attachedBath = ""
    private var belcony = ""
    private var furnistType = ""
    private var floorType = ""
    private var rentForMonth = ""
    private var rent = ""
    private var electricityBill = ""
    private var maintanenceBill = ""
    private var gasBill = ""
    private var waterBill = ""


    constructor()
    constructor(adId: String, adTitle: String, adBoutHome: String, homeType: String, flatName: String, rentForWhome: String, security: String, gas: String,
                lift: String, generator: String, religionName: String, homeLordId: String,  flatId: String,  squareFeet: String, persons : String,  totalRooms: String,
                totalBaths: String,  attachedBath: String,  belcony: String, furnistType: String,  floorType: String, rentForMonth : String,  rent: String,
                electricityBill: String,  maintanenceBill: String, gasBill : String, waterBill : String) {
        this.adId = adId
        this.adTitle = adTitle
        this.adBoutHome = adBoutHome
        this.homeType = homeType
        this.flatName = flatName
        this.rentForWhome = rentForWhome
        this.security = security
        this.gas = gas
        this.lift = lift
        this.generator = generator
        this.religionName = religionName
        this.homeLordId = homeLordId
        this.flatId = flatId
        this.squareFeet = squareFeet
        this.persons = persons
        this.totalRooms = totalRooms
        this.totalBaths = totalBaths
        this.attachedBath = attachedBath
        this.belcony = belcony
        this.furnistType = furnistType
        this.floorType = floorType
        this.rentForMonth = rentForMonth
        this.rent = rent
        this.electricityBill = electricityBill
        this.maintanenceBill = maintanenceBill
        this.gasBill = gasBill
        this.waterBill = waterBill
    }

    fun getAdId(): String {
        return adId
    }

    fun setAdId(adId: String) {
        this.adId = adId
    }

    fun getAdTitle(): String {
        return adTitle
    }

    fun setAdTitle(adTitle: String) {
        this.adTitle = adTitle
    }

    fun getAdBoutHome(): String {
        return adBoutHome
    }

    fun setAdBoutHome(adBoutHome: String) {
        this.adBoutHome = adBoutHome
    }

    fun getHomeType(): String {
        return homeType
    }

    fun setHomeType(homeType: String) {
        this.homeType = homeType
    }

    fun getFlatName(): String {
        return flatName
    }

    fun setFlatName(flatName: String) {
        this.flatName = flatName
    }

    fun getRentForWhome(): String {
        return rentForWhome
    }

    fun setRentForWhome(rentForWhome: String) {
        this.rentForWhome = rentForWhome
    }

    fun getSecurity(): String {
        return security
    }

    fun setSecurity(security: String) {
        this.security = security
    }

    fun getGas(): String {
        return gas
    }

    fun setGas(gas: String) {
        this.gas = gas
    }

    fun getLift(): String {
        return lift
    }

    fun setLift(lift: String) {
        this.lift = lift
    }

    fun getGenerator(): String {
        return generator
    }

    fun setGenerator(generator: String) {
        this.generator = generator
    }

    fun getReligionName(): String {
        return religionName
    }

    fun setReligionName(religionName: String) {
        this.religionName = religionName
    }
    fun getHomeLordId(): String {
        return homeLordId
    }

    fun setHomeLordId(homeLordId: String) {
        this.homeLordId = homeLordId
    }

    fun getFlatId(): String {
        return flatId
    }

    fun setFlatId(flatId: String) {
        this.flatId = flatId
    }

    fun getSquareFeet(): String {
        return squareFeet
    }

    fun setSquareFeet(squareFeet: String) {
        this.squareFeet = squareFeet
    }
    fun getPersons(): String {
        return persons
    }

    fun setPersons(persons: String) {
        this.persons = persons
    }

    fun getTotalRooms(): String {
        return totalRooms
    }

    fun setTotalRooms(totalRooms: String) {
        this.totalRooms = totalRooms
    }

    fun getTotalBaths(): String {
        return totalBaths
    }

    fun setTotalBaths(totalBaths: String) {
        this.totalBaths = totalBaths
    }
    fun getAttachedBath(): String {
        return attachedBath
    }

    fun setAttachedBath(attachedBath: String) {
        this.attachedBath = attachedBath
    }
    fun getBelcony(): String {
        return belcony
    }

    fun setBelcony(belcony: String) {
        this.belcony = belcony
    }
    fun getFurnistType(): String {
        return furnistType
    }

    fun setFurnistType(furnistType: String) {
        this.furnistType = furnistType
    }
    fun getFloorType(): String {
        return floorType
    }

    fun setFloorType(floorType: String) {
        this.floorType = floorType
    }
    fun getRentForMonth(): String {
        return rentForMonth
    }

    fun setRentForMonth(rentForMonth: String) {
        this.rentForMonth = rentForMonth
    }

    fun getRent(): String {
        return rent
    }
    fun setRent(rent: String) {
        this.rent = rent
    }

    fun getElectricityBill(): String {
        return electricityBill
    }
    fun setElectricityBill(electricityBill: String) {
        this.electricityBill = electricityBill
    }


    fun getMaintanenceBill(): String {
        return maintanenceBill
    }
    fun setMaintanenceBill(maintanenceBill: String) {
        this.maintanenceBill = maintanenceBill
    }


    fun getGasBill(): String {
        return gasBill
    }
    fun setGasBill(gasBill: String) {
        this.gasBill = gasBill
    }


    fun getWaterBill(): String {
        return waterBill
    }
    fun setWaterBill(waterBill: String) {
        this.waterBill = waterBill
    }


}