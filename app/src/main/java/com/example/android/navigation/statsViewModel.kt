package com.example.android.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.android.navigation.database.DiceDao

class statsViewModel(
        val database: DiceDao,
        application: Application) : AndroidViewModel(application)
{

}