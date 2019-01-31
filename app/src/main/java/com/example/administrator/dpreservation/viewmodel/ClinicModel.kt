package com.example.administrator.dpreservation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.clinic.Clinic
import com.example.administrator.dpreservation.data.clinic.ClinicRespository

class ClinicModel internal constructor(private val respository: ClinicRespository):ViewModel(){

    fun getNearClinic() = LivePagedListBuilder<Int,Clinic>(respository.getNearClick(), PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(40)
        .build())
        .build()


     fun initData(){
        val list = ArrayList<Clinic>()
        for (i in 0..30){
            val clinic = Clinic("$i 诊所")
            list.add(clinic)
        }
        respository.addClick(list)
    }
}