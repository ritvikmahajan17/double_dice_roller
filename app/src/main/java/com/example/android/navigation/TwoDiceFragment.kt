package com.example.android.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.navigation.TwoDiceFragmentDirections.actionTwoDiceFragmentToStatsFragment
import com.example.android.navigation.databinding.OneDiceFragmentBinding
import com.example.android.navigation.databinding.TwoDiceFragmentBinding


/**
 * A simple [Fragment] subclass.
 */


@Suppress("UNREACHABLE_CODE", "DEPRECATED_IDENTITY_EQUALS")
class TwoDiceFragment : Fragment() {

     var num:Int=0
    var countTotal2:Int=0
    var countTwelve:Int=0
    var countTotal1:Int=0
    var countSix:Int=0

    //Inflating and Returning the View with DataBindingUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      val binding: TwoDiceFragmentBinding = DataBindingUtil.inflate<TwoDiceFragmentBinding>(inflater, R.layout.two_dice_fragment, container, false)

        setHasOptionsMenu(true)

            binding.constraintLayoutTwo.setOnClickListener  {


                countTotal2++
                num = rollDice(binding)

            }

        binding.statsButton2.setOnClickListener {
            view!!.findNavController().navigate(TwoDiceFragmentDirections.actionTwoDiceFragmentToStatsFragment(countSix,countTotal1,countTotal2,countTwelve))
        }

        return binding.root
    }

    //to roll dice

     private fun rollDice(binding:TwoDiceFragmentBinding):Int
    {
        val randomNum1 = java.util.Random().nextInt(6) + 1
        var drawableResource1 = when(randomNum1){
            1-> R.drawable.dice_1
            2-> R.drawable.dice_2
            3-> R.drawable.dice_3
            4-> R.drawable.dice_4
            5-> R.drawable.dice_5
            else-> R.drawable.dice_6
        }

        val randomNum2 = java.util.Random().nextInt(6) + 1
        var drawableResource2 = when(randomNum2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

      var add = randomNum1 + randomNum2

        if(add==12)
        {
            countTwelve++
            binding.totalText.setTextColor(Color.RED)
            binding.totalText.text = add.toString()

        }

        else
        {
            binding.totalText.setTextColor(Color.BLACK)
            binding.totalText.text = add.toString()
        }

        binding.dice1Image.setImageResource(drawableResource1)
        binding.dice2Image.setImageResource(drawableResource2)

        return add

    }


    //to have share button
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {


            super.onCreateOptionsMenu(menu, inflater)
            inflater?.inflate(R.menu.winner_menu, menu)


            if ( null == getShareIntent().resolveActivity(activity!!.packageManager) )
            {
                 menu?.findItem(R.id.share)?.isVisible = false
            }

    }


    private fun getShareIntent(): Intent {

        val shareIntent = Intent(Intent.ACTION_SEND)
         if(num==12) {
             return ShareCompat.IntentBuilder.from(activity)
                     .setText(getString(R.string.share_success_text_two))
                     .setType("text/plain")
                     .intent
         }
        else
             return ShareCompat.IntentBuilder.from(activity)
                     .setText(getString(R.string.share_text_two))
                     .setType("text/plain")
                     .intent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.share -> shareSuccess()
        }

        return super.onOptionsItemSelected(item)

    }



}




