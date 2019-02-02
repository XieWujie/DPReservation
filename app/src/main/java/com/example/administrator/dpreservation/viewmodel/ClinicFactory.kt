package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.dpreservation.data.doctor.DoctorRespository

class ClinicFactory(private val respository: DoctorRespository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClinicModel(respository) as T
    }
}