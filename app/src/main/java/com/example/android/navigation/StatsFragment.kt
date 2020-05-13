package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.navigation.StatsFragmentArgs.fromBundle
import com.example.android.navigation.databinding.OneDiceFragmentBinding
import com.example.android.navigation.databinding.StatsLayoutBinding

/**
 * A simple [Fragment] subclass.
 */
class StatsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: StatsLayoutBinding = DataBindingUtil.inflate<StatsLayoutBinding>(inflater, R.layout.stats_layout, container, false)

        val args = StatsFragmentArgs.fromBundle(arguments!!)

        val notRolled: String = "Not Rolled"
        val none: String = "0"

        binding.statsConstraint.setOnClickListener {


            if (args.countTotal1==0 && args.countSix == 0) //
                binding.oneSix.text = notRolled
            else if (args.countSix==0)
                binding.oneSix.text= none
            else
                binding.oneSix.text = args.countSix.toString()

            if (args.countTotal1 == 0)
                binding.oneTotal.text = notRolled
            else
                binding.oneTotal.text = args.countTotal1.toString()


            if (args.countTotal2==0 && args.countTwelve == 0)
                binding.twoTwelve.text = notRolled
            else if (args.countTwelve==0)
            binding.twoTwelve.text=none
            else
                binding.twoTwelve.text = args.countTwelve.toString()

            if (args.countTotal2 == 0)
                binding.twoTotal.text = notRolled
            else
                binding.twoTotal.text = args.countTotal2.toString()

        }

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

    }


