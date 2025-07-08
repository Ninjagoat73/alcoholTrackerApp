package com.example.alcoholtracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alcoholtracker.data.local.dao.DrinkDao
import com.example.alcoholtracker.data.model.Drink


@Database(entities = [Drink::class], version = 1, exportSchema = false)
abstract class DrinksDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao

    companion object {
        @Volatile private var INSTANCE: DrinksDatabase? = null

        fun getDatabase(context: Context): DrinksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinksDatabase::class.java,
                    "drinks_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}