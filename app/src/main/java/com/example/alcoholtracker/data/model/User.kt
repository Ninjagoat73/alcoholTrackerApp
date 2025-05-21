package com.example.alcoholtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @ColumnInfo("userId")
    @PrimaryKey() val userId: String,

    @ColumnInfo("userName")
    var userName: String,

    @ColumnInfo("userEmail")
    var userEmail: String,

)
