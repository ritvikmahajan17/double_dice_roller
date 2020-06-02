package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.OneDiceFragmentBinding
import com.example.android.navigation.databinding.StatsLayoutBinding

/**
 * A simple [Fragment] subclass.
 */
class StatsFragment : Fragment() {

    private lateinit var sViewModel: statsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: StatsLayoutBinding = DataBindingUtil.inflate<StatsLayoutBinding>(inflater, R.layout.stats_layout, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = statsViewModelFactory(dataSource, application)

        sViewModel = ViewModelProviders.of(this, viewModelFactory).get(statsViewModel::class.java)

        sViewModel.Totalrolls_mode1.observe(this, Observer { value ->

            binding.oneTotal.text = value.toString()

        })

        sViewModel.Totalrolls_mode2.observe(this, Observer { value ->

            binding.twoTotal.text = value.toString()

        })

        sViewModel.Totalsix.observe(this, Observer { value ->

            binding.oneSix.text = value.toString()

        })

        sViewModel.Totaltwelve.observe(this, Observer { value ->

            binding.twoTwelve.text = value.toString()

        })


        setHasOptionsMenu(true)

        return binding.root
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {


        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)


        if ( null == getShareIntent().resolveActivity(activity!!.packageManager) )
        {
            menu?.findItem(R.id.share)?.isVisible = false
        }

    }


    private fun getShareIntent(): Intent {

        val args = StatsFragmentArgs.fromBundle(arguments!!)

        val shareIntent = Intent(Intent.ACTION_SEND)

        if (args.countTotal2 == 0)
        {
            return ShareCompat.IntentBuilder.from(activity)
                    .setText(getString(R.string.share_text_one,args.countSix,args.countTotal1))
                    .setType("text/plain")
                    .intent
        }

        else
        {
            return ShareCompat.IntentBuilder.from(activity)
                    .setText(getString(R.string.share_text_two,args.countTwelve,args.countTotal2))
                    .setType("text/plain")
                    .intent
        }
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

 */

    }


