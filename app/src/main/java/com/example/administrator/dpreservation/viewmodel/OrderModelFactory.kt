package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.dpreservation.data.order.OrderRepository

class OrderModelFactory(private val respository:OrderRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderModel(respository) as T
    }
}