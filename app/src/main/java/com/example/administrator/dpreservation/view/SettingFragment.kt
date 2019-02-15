package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.data.cache.GlideCacheUtil
import com.example.administrator.dpreservation.databinding.FragmentSettingBinding
import com.example.administrator.dpreservation.presenter.SettingPresenter

class SettingFragment:Fragment(){

    private lateinit var binding:FragmentSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        binding.presenter = SettingPresenter()
        event()
        return binding.root
    }

    private fun event(){
        val size = GlideCacheUtil.getCacheSize(requireContext())
        binding.cacheSize.text = size
    }
}