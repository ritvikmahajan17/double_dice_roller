package com.example.android.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.android.navigation.database.DiceDao
import com.example.android.navigation.database.DoubleDice
import kotlinx.coroutines.*

class statsViewModel(
        val database: DiceDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _Totalrolls = MutableLiveData<Int>()
     val Totalrolls :LiveData<Int>
       get() = _Totalrolls

  private  val _Totalsix = MutableLiveData<Int>()
    val Totalsix :LiveData<Int>
        get() = _Totalsix



    init {
        getRolls()
        getSix()
    }

   private fun getRolls()
    {
        uiScope.launch {

            _Totalrolls.value = getTotalRolls()
        }
    }

   private fun getSix(){

        uiScope.launch {
            getTotalSix()
            _Totalsix.value = getTotalSix()

        }
    }

    private suspend fun getTotalRolls() :Int
    {
        return withContext(Dispatchers.IO){

         val rolls :Int = database.getRollID()

            rolls

        }
    }

    private suspend fun getTotalSix() :Int
    {
        return withContext(Dispatchers.IO){

            val six :Int = database.getSix()

            six

        }
    }


}