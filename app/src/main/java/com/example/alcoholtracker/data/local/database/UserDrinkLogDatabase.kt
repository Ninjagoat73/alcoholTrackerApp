package com.example.alcoholtracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.alcoholtracker.data.local.dao.UserDrinkLogDao
import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.di.DateConverter

@Database(entities = [UserDrinkLog::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class UserDrinkLogDatabase : RoomDatabase() {
    abstract fun userDrinkLogDao(): UserDrinkLogDao

    companion object {
        @Volatile private var INSTANCE: UserDrinkLogDatabase? = null

        fun getDatabase(context: Context): UserDrinkLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDrinkLogDatabase::class.java,
                    "user_drink_logs_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}