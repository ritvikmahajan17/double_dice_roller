package com.example.android.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.navigation.database.DiceDao
import com.example.android.navigation.database.DoubleDice
import kotlinx.coroutines.*

class TwoDiceViewModel (
        val database: DiceDao,
        application: Application) : AndroidViewModel(application)
{

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

     val rolls2 = database.getAllRolls()

    private val randomNum = MutableLiveData<Int>() //used in vm
        val _randomNum : LiveData<Int>                  //used in ui
             get() = randomNum

    private val _gotoAllRolls = MutableLiveData<Boolean>()
    val gotoAllRolls : LiveData<Boolean>
        get() = _gotoAllRolls

    fun ontoStats()
    {
        _gotoAllRolls.value = true
    }

    var n1:Int = -1
    var n2:Int = -1

    fun getNum1():Int{
         n1= java.util.Random().nextInt(6) + 1
            return n1
    }

    fun getNum2():Int{
         n2= java.util.Random().nextInt(6) + 1
            return n2
    }

    fun getValue():Int? {
        randomNum.value = getNum1() + getNum2()
        return randomNum.value
    }

    fun rollValue() {

        uiScope.launch {
            val newRoll = DoubleDice()
            val value = getValue()
            newRoll.rollValue_mode2_d1 =n1
            newRoll.rollValue_mode2_d2 =n2
            newRoll.rollValue_mode2 = value
                insert(newRoll)

            if (value==12)
              updateTwelve()

            else {
                updateRollmode2()
            }
        }
    }

    private suspend fun updateRollmode2() {
        withContext(Dispatchers.IO) {

            database.updateRollMode2()
        }
    }

    private suspend fun insert(roll : DoubleDice) {
        withContext(Dispatchers.IO) {

            database.insert(roll)
        }
    }

    private suspend fun updateTwelve() {
        withContext(Dispatchers.IO) {

            database.updateTwelve()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}