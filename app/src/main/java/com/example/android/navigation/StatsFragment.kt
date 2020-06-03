package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.StatsLayoutBinding

/**
 * A simple [Fragment] subclass.
 */
class StatsFragment : Fragment() {

    private lateinit var sViewModel: StatsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: StatsLayoutBinding = DataBindingUtil.inflate<StatsLayoutBinding>(inflater, R.layout.stats_layout, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = statsViewModelFactory(dataSource, application)

        sViewModel = ViewModelProviders.of(this, viewModelFactory).get(StatsViewModel::class.java)

        binding.statsVM = sViewModel

        binding.lifecycleOwner = this

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

        sViewModel.showInitial.observe(this, Observer { value ->
            if(value) {
                binding.oneTotal.text = "0"
                binding.twoTotal.text = "0"
                binding.oneSix.text = "0"
                binding.twoTwelve.text = "0"
                sViewModel.mode1_six = 0
                sViewModel.mode1_roll = 0
                sViewModel.mode2_twelve = 0
                sViewModel.mode2_roll = 0
            }

        })


        setHasOptionsMenu(true)

        return binding.root
    }

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


            return ShareCompat.IntentBuilder.from(activity)
                    .setText(getString(R.string.share_text_one,sViewModel.mode1_six,sViewModel.mode1_roll,sViewModel.mode2_twelve,sViewModel.mode2_roll))
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


