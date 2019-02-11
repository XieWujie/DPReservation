package com.example.administrator.dpreservation.data.order

import androidx.paging.DataSource
import androidx.room.*
import com.example.administrator.dpreservation.utilities.NOT_START
import com.example.administrator.dpreservation.utilities.STARTING

@Dao
interface OrderDao{

    @Query("SELECT * FROM `order` WHERE patientId = :ownerId ORDER BY orderTime DESC")
    fun getOwnerOrder(ownerId:String):DataSource.Factory<Int,Order>

    @Query("SELECT * FROM `order` WHERE doctorId = :doctorId AND orderTime>=:startTime")
    fun getDoctorOrder(doctorId:String,startTime:Long):DataSource.Factory<Int,Order>

    @Query("SELECT * FROM `order` WHERE patientId = :owerId AND state = :type ORDER BY orderTime DESC")
    fun getTypeOrder(owerId:String,type:Int):DataSource.Factory<Int,Order>

    @Query("SELECT * FROM `order`WHERE patientId = :ownerId AND state=$NOT_START AND orderTime<:time")
    fun getStartOrder(ownerId: String,time:Long):DataSource.Factory<Int,Order>

    @Query("SELECT * FROM `order`WHERE patientId = :ownerId AND state=$NOT_START AND orderTime>:time")
    fun getNotStartOrder(ownerId: String,time:Long):DataSource.Factory<Int,Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order:Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list:List<Order>)

    @Delete
    fun delete(order: Order)

}