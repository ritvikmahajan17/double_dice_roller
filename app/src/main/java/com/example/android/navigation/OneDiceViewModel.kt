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



  //  init {
      //  setInitialValue()
  //  }

     private fun setInitialValue() {
       uiScope.launch {
         val newRoll = DoubleDice()
           newRoll.noSix = 0
           newRoll.noTwelve = 0
           newRoll.NoRoll1 =0
         insert(newRoll)
       }

     }

    private suspend fun insert(rollx : DoubleDice)
    { withContext(Dispatchers.IO){
        database.insert(rollx)
    }

    }

    private fun getValue():Int? {
        randomNum.value= java.util.Random().nextInt(6) + 1
                return randomNum.value
    }

    fun rollValue() {
        Log.i("ritvik","rollValuefunc")
        uiScope.launch {
            val newRoll = DoubleDice()

            if(getValue() == 6) {

                newRoll.noSix = newRoll.noSix?.plus(1)
                newRoll.NoRoll1 = newRoll.NoRoll1.plus(1)

                insert(newRoll)
            }
            else {
                newRoll.NoRoll1 = newRoll.NoRoll1.plus(1)
                insert(newRoll)
            }
        }
    }

    private suspend fun update(rollx : DoubleDice)
    {
        withContext(Dispatchers.IO) {
            database.update(rollx)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}