package com.example.administrator.dpreservation.adapter

import com.example.administrator.dpreservation.data.evaluation.Evaluation
import com.example.administrator.dpreservation.databinding.EvaluationItemBinding

class EvaluationHolder(val binding:EvaluationItemBinding):BaseHolder(binding.root){

    override fun bind(any: Any) {
        if (any is Evaluation){
            binding.evaluation = any
        }
    }
}