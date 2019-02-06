package com.example.administrator.dpreservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.AttentionListItemBinding

class AttentionAdapter:PagedListAdapter<Doctor,BaseHolder>(NearAdapter.o){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = AttentionListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AttentionHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


}