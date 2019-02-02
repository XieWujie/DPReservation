package com.example.administrator.dpreservation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.administrator.dpreservation.data.user.UserRepository

class UserModel(
    private val userRepository: UserRepository
): ViewModel(){
    val lastUser = userRepository.getLastUser()
}