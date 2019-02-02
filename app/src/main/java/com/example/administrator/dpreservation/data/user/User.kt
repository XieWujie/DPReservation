package com.example.administrator.dpreservation.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val userId:String,
    val name:String,
    val password:String,
    val loginTime:Long,
    val mailBox:String,
    var isLogout:Boolean = true,
    var avatar:String?
)