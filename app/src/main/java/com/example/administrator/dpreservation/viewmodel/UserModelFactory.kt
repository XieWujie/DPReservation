package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.administrator.dpreservation.data.user.UserRepository


class UserModelFactory(private val userRepository: UserRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserModel(userRepository) as T
    }
}