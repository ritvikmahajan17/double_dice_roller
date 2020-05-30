package com.example.android.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.OneDiceFragmentBinding


/**
 * A simple [Fragment] subclass.
 */
class OneDiceFragment : Fragment() {

    var num:Int=0
    var countTotal1:Int=0
    private  lateinit var viewModel : OneDiceViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: OneDiceFragmentBinding = DataBindingUtil.inflate<OneDiceFragmentBinding>(inflater, R.layout.one_dice_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = OneDiceViewModelFactory(dataSource, application)

        Log.i("ritvik","oncreatefunc")
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(OneDiceViewModel::class.java)

        binding.oneDiceVM= viewModel

        binding.lifecycleOwner = this


        //binding.statsButton.setOnClickListener {
         //   view!!.findNavController().navigate(OneDiceFragmentDirections.actionOneDiceFragmentToStatsFragment(countTotal1,countSix,countTwelve,countTotal2))
        //}

        viewModel._randomNum.observe(this, Observer { value ->
            whatToDisplay(binding,value)
        })



        setHasOptionsMenu(true)
             return binding.root
      }



  private fun whatToDisplay(binding: OneDiceFragmentBinding,value:Int){
   Log.i("ritvik","dispfunc")

    var drawableResource1 = when(value) {
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
        binding.totalTextOne.setTextColor(Color.BLACK)
        binding.totalTextOne.text = value.toString()
     }

    binding.diceImageOne.setImageResource(drawableResource1)

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
    if(num==6) {
        return ShareCompat.IntentBuilder.from(activity)
                .setText(getString(R.string.share_success_text_one))
                .setType("text/plain")
                .intent
    }
    else
        return ShareCompat.IntentBuilder.from(activity)
                .setText(getString(R.string.share_text_one))
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






