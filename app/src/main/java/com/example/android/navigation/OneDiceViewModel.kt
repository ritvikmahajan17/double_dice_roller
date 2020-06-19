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

    val rolls1 = database.getAllRolls()

    private val _gotoAllRolls = MutableLiveData<Boolean>()
     val gotoAllRolls : LiveData<Boolean>
      get() = _gotoAllRolls

    fun ontoAllRolls()
    {
        _gotoAllRolls.value = true
    }

    fun getValue():Int? {
        randomNum.value= java.util.Random().nextInt(6) + 1
          return randomNum.value
    }

    fun rollValue() {

        uiScope.launch {
            val newRoll = DoubleDice()
            val value: Int?  = getValue()
            newRoll.rollValue_mode1 = value
            insert(newRoll)

            if(value == 6) {
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

    private suspend fun getValue_mode1(value:Int?){
        withContext(Dispatchers.IO){

            database.getValue1(value)
        }
    }

    private suspend fun update(newRoll:DoubleDice) {
        withContext(Dispatchers.IO) {

            database.update(newRoll)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}