package com.example.administrator.dpreservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.administrator.dpreservation.data.clinic.Clinic
import com.example.administrator.dpreservation.databinding.NearListLayoutBinding

class NearAdapter:PagedListAdapter<Clinic,BaseHolder>(o){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = NearListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = NearHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    companion object {
        val o = MessageDiffCallBack()
        class MessageDiffCallBack: DiffUtil.ItemCallback<Clinic>(){
            override fun areItemsTheSame(oldItem: Clinic, newItem: Clinic): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Clinic, newItem: Clinic): Boolean {
                return oldItem == newItem
            }


        }
    }
}