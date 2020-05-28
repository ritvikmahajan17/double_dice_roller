package com.example.android.navigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DoubleDice::class], version = 1, exportSchema = false)
abstract class DoubleDiceDatabase : RoomDatabase() {

    abstract val diceDao: DiceDao

    companion object {

        @Volatile
        private var INSTANCE: DoubleDiceDatabase? = null

        fun getInstance(context: Context): DoubleDiceDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            DoubleDiceDatabase::class.java,
                            "sleep_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}