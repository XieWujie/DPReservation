package com.example.administrator.dpreservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.NearListLayoutBinding

class NearAdapter:BaseAdapter<Doctor>(o){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = NearListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = NearHolder(binding)
        return holder
    }




    companion object {
        val o = MessageDiffCallBack()
        class MessageDiffCallBack: DiffUtil.ItemCallback<Doctor>(){
            override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem == newItem
            }


        }
    }
}