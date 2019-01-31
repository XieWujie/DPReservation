package com.example.administrator.dpreservation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder(val view:View):RecyclerView.ViewHolder(view){

    protected val context = view.context

    abstract fun bind(any:Any)
}