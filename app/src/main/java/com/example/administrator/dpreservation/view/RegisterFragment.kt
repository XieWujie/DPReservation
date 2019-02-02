package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.databinding.FragmentRegisterBinding
import com.example.administrator.dpreservation.presenter.RegisterPresenter

class RegisterFragment:Fragment(){

    private lateinit var binding:FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerhelper = RegisterPresenter()
        return binding.root
    }
}