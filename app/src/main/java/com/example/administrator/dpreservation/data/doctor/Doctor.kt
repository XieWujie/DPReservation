package com.example.administrator.dpreservation.data.doctor

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.administrator.dpreservation.data.Position
import java.io.Serializable

@Entity
data class Doctor(
    @PrimaryKey
    val name:String,
    val avatar:String?,
    @Embedded val  position: Position,
    val qualification:String,
    val education:String,
    val doctorCertification:Boolean,
    val authentication:Boolean,
    val workerTime:String,
    val graduatedSchool:String,
    val workerAddress:String,
    val phone:String,
    val historyOrderCount:Int,
    val goodAt:String,
    val praise:Float

):Serializable