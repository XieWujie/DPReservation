package com.example.administrator.dpreservation.data.clinic

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ClinicDao{

    @Query("SELECT * FROM clinic")
    fun getNearCinic():DataSource.Factory<Int,Clinic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addClinic(list:List<Clinic>)
}