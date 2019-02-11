package com.example.administrator.dpreservation.adapter

import android.content.Intent
import android.os.Bundle
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.NearListLayoutBinding
import com.example.administrator.dpreservation.view.DoctorActivity

class NearHolder(val binding:NearListLayoutBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Doctor){
            binding.doctor = any
            binding.root.setOnClickListener {
                val intent = Intent(it.context,DoctorActivity::class.java)
                intent.putExtra("doctor",any)
                it.context.startActivity(intent)
            }
            binding.toLocate.setOnClickListener {
                listenter?.event(any)
            }
        }
    }
}