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

    @Query("SELECT mode1_roll from roll_table ORDER BY mode1_roll DESC LIMIT 1")
    fun getTotal6():Int

    @Query("SELECT mode2_roll from roll_table ORDER BY mode2_roll DESC LIMIT 1")
    fun getTotal12():Int

    @Query("UPDATE roll_table SET six_roll = six_roll + 1")
    fun updateSix()

   @Query("UPDATE roll_table SET roll_value_mode1 = :value1")
    fun getValue1(value1 : Int?)

//    @Query("INSERT INTO roll_table ( roll_value_mode1) VALUES ( :value1 )")
//    fun getValue1(value1 : Int?)

//    @Query("UPDATE roll_table SET roll_value_mode2 = :value2")
//    fun getValue2(value : Int?):Int

    @Query("UPDATE roll_table SET mode1_roll = mode1_roll + 1 ")
    fun updateRollMode1()

    @Query("UPDATE roll_table SET mode2_roll = mode2_roll + 1 ")
    fun updateRollMode2()

    @Query("UPDATE roll_table SET twelve_roll = twelve_roll + 1 ")
    fun updateTwelve()

    @Query("SELECT six_roll from roll_table  ORDER BY six_roll DESC LIMIT 1 ")
    fun getSix():Int

    @Query("SELECT twelve_roll from roll_table ORDER BY twelve_roll DESC LIMIT 1")
    fun getTwelve():Int

    @Query("SELECT rollID from roll_table ORDER BY rollID DESC LIMIT 1")
    fun getRollID():Int

    @Query("SELECT * from roll_table ORDER BY rollID DESC")
   fun getAllRolls():LiveData<List<DoubleDice>>

    @Query("DELETE FROM roll_table")
    fun clear()

}