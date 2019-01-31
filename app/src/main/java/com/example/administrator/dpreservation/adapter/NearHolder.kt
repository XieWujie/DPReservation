package com.example.administrator.dpreservation.adapter

import com.example.administrator.dpreservation.data.clinic.Clinic
import com.example.administrator.dpreservation.databinding.NearListLayoutBinding

class NearHolder(val binding:NearListLayoutBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Clinic){
            binding.clinic = any
        }
    }
}