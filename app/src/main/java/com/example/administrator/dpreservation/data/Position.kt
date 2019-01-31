package com.example.administrator.dpreservation.data

data class Position(
    val country:String,
    val province:String,
    val city:String,
    val district:String,
    val streetNumber:String,
    val latitude:Double,
    val longitude:Double
)