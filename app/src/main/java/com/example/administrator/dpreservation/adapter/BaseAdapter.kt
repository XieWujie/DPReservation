package com.example.administrator.dpreservation.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BaseAdapter<E>(diff:DiffUtil.ItemCallback<E>):PagedListAdapter<E,BaseHolder>(diff){

    var listener:Event? = null

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setEvent(listener)
    }

    fun setEvent(event: Event){
        if (listener == null){
            listener = event
        }
    }

}