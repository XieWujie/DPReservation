package com.example.administrator.dpreservation.data.order

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.administrator.dpreservation.data.doctor.Doctor
import java.io.Serializable

@Entity(tableName = "order")
data class Order(
    @PrimaryKey val id:String,
    val patientId:String,
    val doctorId:String,
    val orderTime:Long,
    val description:String,
    val state:Int,
    val doctorAvatar:String?,
    val doctorName:String,
    val address:String,
    val score:Float,
    val doctorPhone:String,
    val agreeTime:Long,
    val createTime:Long,
    val completeTime:Long,
    val endTreatmentTime:Long
):Serializable