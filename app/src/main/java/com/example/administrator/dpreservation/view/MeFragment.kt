package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.administrator.dpreservation.databinding.FragmentMeBinding
import com.example.administrator.dpreservation.presenter.MePresenter
import java.lang.reflect.Array.setInt
import java.lang.reflect.AccessibleObject.setAccessible
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import android.app.Activity
import android.graphics.Point


class MeFragment:Fragment(){

    private lateinit var binding:FragmentMeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMeBinding.inflate(inflater,container,false)
        binding.presenter = MePresenter()
        return binding.root
    }

}