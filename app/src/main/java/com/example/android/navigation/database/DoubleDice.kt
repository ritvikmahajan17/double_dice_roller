package com.example.android.navigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roll_table")
data class  DoubleDice (

        @PrimaryKey(autoGenerate = true)
        var rollID:Long= 0L,

        @ColumnInfo(name = "mode1_roll")
        var NoRoll1:Int = -1,

        @ColumnInfo(name = "mode2_roll")
        var NoRoll2: Int = -1,

        @ColumnInfo(name = "six_roll")
        var noSix:Int? = 0,

        @ColumnInfo(name = "twelve_roll")
        var noTwelve:Int? = 0


)