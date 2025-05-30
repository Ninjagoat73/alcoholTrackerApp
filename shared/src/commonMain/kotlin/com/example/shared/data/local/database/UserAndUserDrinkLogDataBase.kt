package com.example.shared.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shared.data.local.dao.UserAndUserDrinkLogDao
import com.example.shared.data.model.User
import com.example.shared.data.model.UserDrinkLog
import com.example.shared.di.DateConverter


@Database(entities = [User::class, UserDrinkLog::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class UserAndUserDrinkLogDataBase : RoomDatabase() {
    abstract fun userAndUserDrinkLogDao(): UserAndUserDrinkLogDao

    companion object{

        private fun buildDatabase(context: Context): UserAndUserDrinkLogDataBase {
            return Room.databaseBuilder(context, UserAndUserDrinkLogDataBase::class.java, "userAndUserDrinkLog.db")
                .build()
        }

        @Volatile private var INSTANCE: UserAndUserDrinkLogDataBase? = null

        fun getDatabase(context: Context): UserAndUserDrinkLogDataBase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }
    }


}