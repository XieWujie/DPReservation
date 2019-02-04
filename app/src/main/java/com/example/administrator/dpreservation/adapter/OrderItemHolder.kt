package com.example.administrator.dpreservation.adapter

import com.example.administrator.dpreservation.data.order.Order
import com.example.administrator.dpreservation.databinding.OrderListItemBinding

class OrderItemHolder(private val binding:OrderListItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Order){
            binding.order = any
        }
    }
}