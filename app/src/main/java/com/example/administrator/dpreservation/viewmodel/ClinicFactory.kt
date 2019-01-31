package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.dpreservation.data.clinic.ClinicRespository

class ClinicFactory(private val respository: ClinicRespository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClinicModel(respository) as T
    }
}