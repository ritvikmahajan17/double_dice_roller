package com.example.android.navigation

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.navigation.database.DiceDao
import com.example.android.navigation.database.DoubleDice
import com.example.android.navigation.databinding.OneDiceFragmentBinding
import kotlinx.coroutines.*

class OneDiceViewModel (
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


     fun getValue():Int? {
        randomNum.value= java.util.Random().nextInt(6) + 1
                return randomNum.value
    }

    fun rollValue() {

        uiScope.launch {
            val newRoll = DoubleDice()
            insert(newRoll)

            if(getValue() == 6) {
                updateSix()
               }

            else {
                updateRollmode1()
            }
        }
    }

    private suspend fun insert(roll : DoubleDice) {
        withContext(Dispatchers.IO) {

            database.insert(roll)
        }
    }

    private suspend fun updateRollmode1() {
        withContext(Dispatchers.IO) {

            database.updateRollMode1()
        }
    }

    private suspend fun updateSix() {
        withContext(Dispatchers.IO) {

            database.updateSix()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}