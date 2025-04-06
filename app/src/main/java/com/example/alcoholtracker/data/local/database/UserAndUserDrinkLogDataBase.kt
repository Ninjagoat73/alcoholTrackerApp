package com.example.alcoholtracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alcoholtracker.data.local.dao.UserAndUserDrinkLogDao
import com.example.alcoholtracker.data.model.User
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.di.DateConverter


@Database(entities = [User::class, UserDrinkLog::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class UserAndUserDrinkLogDataBase : RoomDatabase() {
    abstract fun userAndUserDrinkLogDao(): UserAndUserDrinkLogDao

    companion object{

        @Volatile private var INSTANCE: UserAndUserDrinkLogDataBase? = null

        private fun getDatabase(context: Context): UserAndUserDrinkLogDataBase {
            if (INSTANCE == null) {

            }
        }
    }


}