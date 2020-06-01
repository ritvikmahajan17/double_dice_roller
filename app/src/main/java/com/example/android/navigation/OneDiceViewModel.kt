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


    private fun getValue():Int? {
        randomNum.value= java.util.Random().nextInt(6) + 1
                return randomNum.value
    }

    fun rollValue() {

        uiScope.launch {
            val newRoll = DoubleDice()

            if(getValue() == 6) {
                updateSix()
               }

            else {
                insert(newRoll)
            }
        }
    }

    private suspend fun insert(roll : DoubleDice) {
        withContext(Dispatchers.IO) {
            Log.i("ritvik", "inserte called")
            database.insert(roll)
        }
    }

    private suspend fun updateSix() {
        withContext(Dispatchers.IO) {
            Log.i("ritvik", "inserte called")
            database.updateSix()
        }
    }

       private suspend fun getSix() : Int
        {return withContext(Dispatchers.IO){
            Log.i("ritvik","getSi called")
            var prevSix = database.getSix()

            prevSix
        }
    }

    private suspend fun update(roll : DoubleDice)
    {
        withContext(Dispatchers.IO) {
            database.update(roll)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}