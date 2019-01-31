package com.example.administrator.dpreservation.data.clinic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Clinic(
    @PrimaryKey
    val name:String
)