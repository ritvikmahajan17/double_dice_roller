package com.example.android.navigation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiceDao {

    @Insert
    fun insert(roll:DoubleDice)

    @Update
    fun update(roll:DoubleDice)

    @Query("SELECT mode1_roll from roll_table")
    fun getTotal6():Int

    @Query("SELECT mode2_roll from roll_table")
    fun getTotal12():Int

    @Query("SELECT six_roll from roll_table")
    fun getSix():Int

    @Query("SELECT twelve_roll from roll_table")
    fun getTwelve():Int

    @Query("DELETE FROM roll_table")
    fun clear()

    @Query("SELECT * FROM roll_table ORDER BY rollID DESC")
    fun getAllNights(): LiveData<List<DoubleDice>>
}