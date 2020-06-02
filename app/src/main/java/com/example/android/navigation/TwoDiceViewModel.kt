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

    private val randomNum = MutableLiveData<Int>() //used in vm
        val _randomNum : LiveData<Int>                  //used in ui
             get() = randomNum

    private val _gotoStats = MutableLiveData<Boolean>()
    val gotoStats : LiveData<Boolean>
        get() = _gotoStats

    fun ontoStats()
    {
        _gotoStats.value = true
    }

    fun getNum1():Int{
        val num1 =java.util.Random().nextInt(6) + 1
        return num1
    }

    fun getNum2():Int{
        val num2 =java.util.Random().nextInt(6) + 1
        return num2
    }

    fun getValue():Int? {
        randomNum.value = getNum1() + getNum2()
        return randomNum.value
    }

    fun rollValue() {

        uiScope.launch {
            val newRoll = DoubleDice()
                insert(newRoll)

            if (getValue()==12)
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