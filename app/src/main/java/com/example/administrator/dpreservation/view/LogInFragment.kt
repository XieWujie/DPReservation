package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.databinding.FragmentLoginBinding
import com.example.administrator.dpreservation.presenter.LoginPresenter

class LogInFragment:Fragment(){

    lateinit var binding:FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container,false)
        binding.loginhelper = LoginPresenter("","")
        return binding.root
    }
}