package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.FragmentDoctorDetailBinding

class DoctorDetailFragment:Fragment(){

    private lateinit var binding:FragmentDoctorDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDoctorDetailBinding.inflate(inflater, container,false)
        val doctor = activity?.intent?.getSerializableExtra("doctor")
        if (doctor is Doctor){
            binding.doctor = doctor
        }
        binding.floatbar.setOnClickListener {
            it.findNavController().navigate(R.id.action_doctorDetailFragment_to_reservationFragment)
        }
        return binding.root
    }
}