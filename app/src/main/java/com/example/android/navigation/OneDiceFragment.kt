package com.example.android.navigation

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.OneDiceFragmentBinding

//lateinit var viewModel : OneDiceViewModel

class OneDiceFragment : Fragment() {

    private lateinit var viewModel : OneDiceViewModel
    private val myColor:Int = Color.parseColor("#EEA47F")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: OneDiceFragmentBinding = DataBindingUtil.inflate<OneDiceFragmentBinding>(inflater, R.layout.one_dice_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = OneDiceViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(OneDiceViewModel::class.java)

        binding.oneDiceVM= viewModel

        binding.lifecycleOwner = this

        viewModel._randomNum.observe(this, Observer { value ->
            whatToDisplay(binding,value)

        })

        viewModel.gotoAllRolls.observe(this, Observer { value ->
            if(value)
                findNavController().navigate(OneDiceFragmentDirections.actionOneDiceFragmentToAllRollFragment())

        })

        setHasOptionsMenu(true)
             return binding.root
      }

  private fun whatToDisplay(binding: OneDiceFragmentBinding,value:Int){


    val drawableResource1 = when(value) {
         1-> R.drawable.dice_1
         2-> R.drawable.dice_2
         3-> R.drawable.dice_3
         4-> R.drawable.dice_4
         5-> R.drawable.dice_5
          else-> R.drawable.dice_6
    }

    if(value==6)
     {
        binding.totalTextOne.setTextColor(Color.RED)
        binding.totalTextOne.text = value.toString()
     }

    else
     {
        binding.totalTextOne.setTextColor(myColor)
        binding.totalTextOne.text =value.toString()
     }

    binding.diceImageOne.setImageResource(drawableResource1)

    }

}






