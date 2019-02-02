package com.example.administrator.dpreservation.adapter


import com.example.administrator.dpreservation.adapter.BaseHolder
import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.databinding.LeftLayoutTextBinding




class LeftTextHolder(val binding: LeftLayoutTextBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Message){
            binding.message = any
        }
    }
}