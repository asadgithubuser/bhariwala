package com.example.bhariwala.Models

class Flat {
    private var flatId: String = ""
    private var homeLordId: String = ""
    private var propertyId: String = ""
    private var propertyName: String = ""
    private var flatName: String = ""
    private var isBooked: String = ""
    private var flatImages: String = ""
    private var squareFeet: String = ""
    private var persons: String = ""
    private var totalRooms: String = ""
    private var totalBaths: String = ""
    private var attachedBath: String = ""
    private var belcony: String = ""
    private var furnistType: String = ""
    private var floorType: String = ""
    private var rentForMonth: String = ""
    private var rent: String = ""
    private var electricityBill: String = ""
    private var maintanenceBill: String = ""
    private var gasBill: String = ""
    private var waterBill: String = ""

    constructor()
    constructor(
        flatId: String,
        homeLordId: String,
        propertyId: String,
        propertyName: String,
        flatName: String,
        isBooked: String,
        flatImages: String,
        squareFeet: String,
        persons: String,
        totalRooms: String,
        totalBaths: String,
        attachedBath: String,
        belcony: String,
        furnistType: String,
        floorType: String,
        rentForMonth: String,
        rent: String,
        electricityBill: String,
        maintanenceBill: String,
        gasBill: String,
        waterBill: String
    ) {
        this.flatId = flatId
        this.homeLordId = homeLordId
        this.propertyId = propertyId
        this.propertyName = propertyName
        this.flatName = flatName
        this.isBooked = isBooked
        this.flatImages = flatImages
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

    fun getFlatId():String{
        return flatId
    }
    fun getHomeLordId():String{
        return homeLordId
    }
    fun getPropertyId():String{
        return propertyId
    }

    fun setHomeLordId(homeLordId: String){
        this.homeLordId = homeLordId
    }
    fun setPropertyId(propertyId: String){
        this.propertyId = propertyId
    }

    fun setFlatId(flatId: String){
        this.flatId = flatId
    }

    fun setPropertyName(propertyName: String){
        this.propertyName = propertyName
    }


    fun getPropertyName():String{
        return propertyName
    }


    fun getFlatName():String{
        return flatName
    }

    fun setFlatName(flatName: String){
        this.flatName = flatName
    }

    fun getIsBooked():String{
        return isBooked
    }

    fun setIsBooked(isBooked: String){
        this.isBooked = isBooked
    }


    fun getfFatImages():String{
        return flatImages
    }

    fun setfFatImages(flatImages: String){
        this.flatImages = flatImages
    }


    fun getSquareFeet():String{
        return squareFeet
    }

    fun setSquareFeet(squareFeet: String){
        this.squareFeet = squareFeet
    }


    fun getPersons():String{
        return persons
    }

    fun setPersons(persons: String){
        this.persons = persons
    }


    fun getTotalRooms():String{
        return totalRooms
    }

    fun setTotalRooms(totalRooms: String){
        this.totalRooms = totalRooms
    }


    fun getTotalBaths():String{
        return totalBaths
    }

    fun setTotalBaths(totalBaths: String){
        this.totalBaths = totalBaths
    }


    fun getAttachedBath():String{
        return attachedBath
    }

    fun setAttachedBath(attachedBath: String){
        this.attachedBath = attachedBath
    }


    fun getBelcony():String{
        return belcony
    }

    fun setBelcony(belcony: String){
        this.belcony = belcony
    }


    fun getFurnistType():String{
        return furnistType
    }

    fun setFurnistType(furnistType: String){
        this.furnistType = furnistType
    }


    fun getFloorType():String{
        return floorType
    }

    fun setFloorType(floorType: String){
        this.floorType = floorType
    }
    fun getRentForMonth():String{
        return rentForMonth
    }

    fun setRentForMonth(rentForMonth: String){
        this.rentForMonth = rentForMonth
    }
    fun getRent():String{
        return rent
    }

    fun setRent(rent: String){
        this.rent = rent
    }
    fun getElectricityBill():String{
        return electricityBill
    }

    fun setElectricityBill(electricityBill: String){
        this.electricityBill = electricityBill
    }
    fun getMaintanenceBill():String{
        return maintanenceBill
    }

    fun setMaintanenceBill(maintanenceBill: String){
        this.maintanenceBill = maintanenceBill
    }
    fun getGasBill():String{
        return gasBill
    }

    fun setGasBill(gasBill: String){
        this.gasBill = gasBill
    }
    fun getWaterBill():String{
        return waterBill
    }

    fun setWaterBill(waterBill: String){
        this.waterBill = waterBill
    }


}