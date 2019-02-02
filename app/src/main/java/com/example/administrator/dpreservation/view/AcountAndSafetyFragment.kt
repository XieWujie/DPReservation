package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.databinding.FragmentAcountSafetyBinding
import com.example.administrator.dpreservation.presenter.AcountAndSafetyPresenter

class AcountAndSafetyFragment():Fragment(){

    private lateinit var binding:FragmentAcountSafetyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAcountSafetyBinding.inflate(inflater,container,false)
        activity?.setTitle("账号和安全")
        binding.presenter = AcountAndSafetyPresenter()
        return binding.root
    }
}