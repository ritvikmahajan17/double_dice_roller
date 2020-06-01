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

  //  @Query("SELECT mode1_roll from roll_table")
 //   fun getTotal6():Int

    @Query("UPDATE roll_table SET six_roll = six_roll + 1 ")
    fun updateSix()

    @Query("SELECT six_roll from roll_table  ORDER BY six_roll LIMIT 1 ")
    fun getSix():Int

    @Query("SELECT twelve_roll123 from roll_table ORDER BY twelve_roll123 LIMIT 1")
    fun getTwelve():Int

    @Query("DELETE FROM roll_table")
    fun clear()


}