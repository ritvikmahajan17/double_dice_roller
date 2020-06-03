package com.example.android.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.android.navigation.database.DiceDao
import com.example.android.navigation.database.DoubleDice
import kotlinx.coroutines.*

class StatsViewModel(
        val database: DiceDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    var mode2_roll:Int? =null
    var mode1_roll:Int? =null
    var mode2_twelve:Int? =null
    var mode1_six:Int? =null

    private val _Totalrolls_mode1 = MutableLiveData<Int>()
     val Totalrolls_mode1 :LiveData<Int>
       get() = _Totalrolls_mode1

    private val _Totalrolls_mode2 = MutableLiveData<Int>()
    val Totalrolls_mode2 :LiveData<Int>
        get() = _Totalrolls_mode2

  private  val _Totalsix = MutableLiveData<Int>()
    val Totalsix :LiveData<Int>
        get() = _Totalsix

    private  val _Totaltwelve = MutableLiveData<Int>()
    val Totaltwelve :LiveData<Int>
        get() = _Totaltwelve

    private  val _showInitial = MutableLiveData<Boolean>()
    val showInitial :LiveData<Boolean>
        get() = _showInitial


    init {
        getRolls_mode1()
        getRolls_mode2()
        getSix()
        getTwelve()

    }

    fun getRolls_mode2()
    {
        uiScope.launch {

            _Totalrolls_mode2.value = getTotalRolls_mode2()
            mode2_roll = getTotalRolls_mode2()
        }
    }

     fun getRolls_mode1()
    {
        uiScope.launch {

            _Totalrolls_mode1.value = getTotalRolls_mode1()
            mode1_roll = getTotalRolls_mode1()
        }
    }


    private fun getSix(){

        uiScope.launch {

            _Totalsix.value = getTotalSix()
            mode1_six = getTotalSix()

        }
    }

    private fun getTwelve(){

        uiScope.launch {

            _Totaltwelve.value = getTotalTwelve()
            mode2_twelve = getTotalTwelve()

        }
    }

     fun ClearStats()
    {   uiScope.launch {
            clear()
        _showInitial.value=true
    }
    }

    private suspend fun getTotalRolls_mode1() :Int
    {
        return withContext(Dispatchers.IO){

         val rolls :Int = database.getTotal6()

            rolls

        }
    }

    private suspend fun getTotalRolls_mode2() :Int
    {
        return withContext(Dispatchers.IO){

            val rolls :Int = database.getTotal12()

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

    private suspend fun getTotalTwelve() :Int
    {
        return withContext(Dispatchers.IO){

            val twelve :Int = database.getTwelve()

            twelve

        }
    }

    private suspend fun clear()
    {
         withContext(Dispatchers.IO){
           database.clear()
        }
    }


}