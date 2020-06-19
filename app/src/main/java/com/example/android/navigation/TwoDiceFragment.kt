package com.example.android.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.TwoDiceFragmentBinding



@Suppress("UNREACHABLE_CODE", "DEPRECATED_IDENTITY_EQUALS")
class TwoDiceFragment : Fragment() {

           private  lateinit var viewModel : TwoDiceViewModel
            private val myColor:Int = Color.parseColor("#EEA47F")

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      val binding: TwoDiceFragmentBinding = DataBindingUtil.inflate<TwoDiceFragmentBinding>(inflater, R.layout.two_dice_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = TwoDiceViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(TwoDiceViewModel::class.java)

        binding.twoDiceVM= viewModel

        binding.lifecycleOwner = this


            viewModel._randomNum.observe(this, Observer { value ->
                display(binding,value)
            })

        viewModel.gotoAllRolls.observe(this, Observer { value ->
            if(value)
                findNavController().navigate(TwoDiceFragmentDirections.actionTwoDiceFragmentToAllRollFragment2())

        })
        setHasOptionsMenu(true)
        return binding.root
    }

    //to roll dice

     private fun display(binding:TwoDiceFragmentBinding,value:Int)
    {

        val drawableResource1 = when(viewModel.n1){
            1-> R.drawable.dice_1
            2-> R.drawable.dice_2
            3-> R.drawable.dice_3
            4-> R.drawable.dice_4
            5-> R.drawable.dice_5
            else-> R.drawable.dice_6
        }


        val drawableResource2 = when(viewModel.n2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        if(value==12)
        {

            binding.totalText.setTextColor(Color.RED)
            binding.totalText.text = value.toString()

        }

        else
        {
            binding.totalText.setTextColor(myColor)
            binding.totalText.text = value.toString()
        }

        binding.dice1Image.setImageResource(drawableResource1)
        binding.dice2Image.setImageResource(drawableResource2)

    }
}




