package com.example.administrator.dpreservation.utilities

import android.content.Context
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.clinic.ClinicRespository
import com.example.administrator.dpreservation.viewmodel.ClinicFactory

object ViewModelFactory{

    private fun getDatabase(context: Context) = AppDatabase.getInstance(context)

    fun getClinicModelFactory(context: Context) =
            ClinicFactory(ClinicRespository.getInstance(getDatabase(context).getClinicDao()))
}