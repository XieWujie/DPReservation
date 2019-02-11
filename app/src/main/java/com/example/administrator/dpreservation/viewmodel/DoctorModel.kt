package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.data.doctor.DoctorRepository

class DoctorModel internal constructor(private val repository: DoctorRepository):ViewModel(){

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(40)
        .build()

    fun getNearClinic(city:String,district:String,street:String) = LivePagedListBuilder<Int,Doctor>(repository.getNearDoctor(city,district,street),config).build()

    val attention = LivePagedListBuilder<Int,Doctor>(repository.getAttention(),config).build()
}