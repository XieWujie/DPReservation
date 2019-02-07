package com.example.administrator.dpreservation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.administrator.dpreservation.adapter.AttentionAdapter
import com.example.administrator.dpreservation.core.DoctorManager
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.databinding.FragmentAttentionBinding
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.utilities.ViewModelFactory
import com.example.administrator.dpreservation.viewmodel.DoctorModel
import com.example.administrator.dpreservation.viewmodel.UserModel
import kotlinx.android.synthetic.main.order_list_item.*

class AttentionFragment:Fragment(){

    private lateinit var binding:FragmentAttentionBinding
    private lateinit var model:DoctorModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAttentionBinding.inflate(inflater,container,false)
        val factory = ViewModelFactory.getClinicModelFactory(requireContext())
        model = ViewModelProviders.of(this,factory)[DoctorModel::class.java]
        binding.setLifecycleOwner(this)
        init()
        return binding.root
    }

    private fun init(){
        val attention = UserManage.atttention
        if (attention != null){
          DoctorManager.requestDoctors(requireContext(),attention){

          }
        }
        val adapter = AttentionAdapter()
        val recyclerView  = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        model.attention.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}