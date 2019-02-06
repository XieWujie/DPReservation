package com.example.administrator.dpreservation.data.order

import com.example.administrator.dpreservation.utilities.runOnNewThread

class OrderRepository private constructor(private val orderDao: OrderDao){


    fun getOwnerOrder(ownerId:String) = orderDao.getOwnerOrder(ownerId)

    fun getDoctorOrder(doctorId:String,startTime:Long) = orderDao.getDoctorOrder(doctorId,startTime)

    fun getTypeOrder(ownerId: String,type:Int) = orderDao.getTypeOrder(ownerId,type)

    fun delete(order: Order){
        runOnNewThread {
            orderDao.delete(order)
        }
    }

    fun addOrders(list: List<Order>){
        runOnNewThread {
            orderDao.insert(list)
        }
    }


    fun addOrder(order: Order){
        runOnNewThread {
            orderDao.insert(order
            )
        }
    }


    companion object {

        @Volatile private var instance: OrderRepository? = null

        fun getInstance(orderDao: OrderDao) =
            instance ?: synchronized(this) {
                instance ?: OrderRepository(orderDao).also { instance = it }
            }
    }
}