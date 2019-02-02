package com.example.administrator.dpreservation.adapter

import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.databinding.LeftLayoutImageBinding


class LeftImageHolder(val bind:LeftLayoutImageBinding):BaseHolder(bind.root){

    override fun bind(any: Any) {
        if (any is Message){
            bind.message = any
        }
    }
}