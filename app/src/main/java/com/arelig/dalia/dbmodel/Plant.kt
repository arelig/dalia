package com.arelig.dalia.dbmodel

/*
    THIS IS THE DATA MODEL

    @todo: activity logic only,db contract, db helper
 */
abstract class Plant(val name: String,
    val category: Int , val care: Int){

    fun setCalendarReminder(){
        //type.watering_frecuency
        //interacts with the data model from calendar
    }
}