package com.example.administrator.dpreservation.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.dpreservation.databinding.TypeListItemBinding

class TypeAdapter() :RecyclerView.Adapter<TypeAdapter.ViewHodler>() {

    private val list = listOf<String>("全部","1","2","3","4","5","6","7")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodler {
        val binding = TypeListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHodler(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
      holder.binding.type = list[position]
    }


    inner class ViewHodler(val binding: TypeListItemBinding):RecyclerView.ViewHolder(binding.root){

    }

}