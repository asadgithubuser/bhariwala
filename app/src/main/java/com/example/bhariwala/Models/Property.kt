package com.example.bhariwala.Models

class Property {
    private var propertyId = ""
    private var homeLordId = ""
    private var buildingName = ""
    private var totalFloor = ""
    private var address = ""
    private var road = ""
    private var house = ""
    private var section = ""
    private var thana = ""
    private var postCode = ""
    private var city = ""
    private var division = ""

    constructor()
    constructor(propertyId: String, homeLordId: String, buildingName: String, totalFloor: String, address: String, road: String, house: String,
    section: String, thana: String, postCode: String, city: String, division: String){

        this.propertyId = propertyId
        this.homeLordId = homeLordId
        this.buildingName = buildingName
        this.totalFloor = totalFloor
        this.address = address
        this.road = road
        this.house = house
        this.section = section
        this.thana = thana
        this.postCode = postCode
        this.city = city
        this.division = division

    }

    fun getPropertyId():String{
        return propertyId
    }
    fun getHomeLordId():String{
        return homeLordId
    }
    fun getBuildingName():String{
        return buildingName
    }
    fun getTotalFloor():String{
        return totalFloor
    }
    fun getAddress():String{
        return address
    }
    fun getRoad():String{
        return road
    }
    fun getHouse():String{
        return house
    }

    fun getSection():String{
        return section
    }

    fun getThana():String{
        return thana
    }

    fun getPostCode():String{
        return postCode
    }

    fun getCity():String{
        return city
    }

    fun getDivision():String{
        return division
    }


    fun setPropertyId(propertyId: String){
        this.propertyId = propertyId
    }
    fun setHomeLordId(homeLordId: String){
        this.homeLordId = homeLordId
    }
    fun setBuildingName(buildingName: String){
        this.buildingName = buildingName
    }
    fun setTotalFloor(totalFloor: String){
        this.totalFloor = totalFloor
    }
    fun setAddress(address: String){
        this.address = address
    }
    fun setRoad(road: String){
        this.road = road
    }
    fun setHouse(house: String){
        this.house = house
    }

    fun setSection(section: String){
        this.section = section
    }

    fun setThana(thana: String){
        this.thana = thana
    }

    fun setPostCode(postCode: String){
        this.postCode = postCode
    }

    fun setCity(city: String){
        this.city = city
    }

    fun setDivision(division: String){
        this.division = division
    }



}