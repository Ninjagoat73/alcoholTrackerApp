package com.example.alcoholtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @ColumnInfo("userId")
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,

    @ColumnInfo("userName")
    var userName: String,

    @ColumnInfo("userEmail")
    var userEmail: String,

    @ColumnInfo("userPassword")
    var userPassword: String

)
