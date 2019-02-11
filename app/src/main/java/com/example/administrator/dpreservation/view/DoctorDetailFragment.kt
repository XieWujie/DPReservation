package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.adapter.EvaluationAdapter
import com.example.administrator.dpreservation.core.EvaluateManager
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.FragmentDoctorDetailBinding
import com.example.administrator.dpreservation.utilities.ViewModelFactory
import com.example.administrator.dpreservation.viewmodel.EvaluationModel

class DoctorDetailFragment:Fragment(){

    private lateinit var binding:FragmentDoctorDetailBinding
    private lateinit var model :EvaluationModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDoctorDetailBinding.inflate(inflater, container,false)
        val factory = ViewModelFactory.getEvaluationFactory(requireContext())
        model = ViewModelProviders.of(this,factory)[EvaluationModel::class.java]
        binding.setLifecycleOwner(this)
        val doctor = activity?.intent?.getSerializableExtra("doctor")
        if (doctor is Doctor){
            binding.doctor = doctor
            init(doctor)
        }
        return binding.root
    }

    fun init(doctor:Doctor){
        EvaluateManager.findDoctorEvaluation(requireContext(),doctor.id){

        }
        val adapter = EvaluationAdapter()
        model.getScore(doctor.id).observe(this, Observer {
            binding.detailsPraiseNumber.text = "$it 分"
        })
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
        model.getDoctorEvaluation(doctor.id).observe(this, Observer {
            adapter.submitList(it)
        })
    }
}