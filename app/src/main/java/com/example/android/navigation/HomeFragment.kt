package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.HomePageBinding
import com.example.android.navigation.databinding.TwoDiceFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: HomePageBinding = DataBindingUtil.inflate<HomePageBinding>(inflater, R.layout.home_page, container, false)

        binding.buttonOne.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_oneDiceFragment2))

        binding.buttonTwo.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_twoDiceFragment))

        binding.buttonAllRolls.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_statsFragment))

        setHasOptionsMenu(true)
        return binding.root
        }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       return NavigationUI.onNavDestinationSelected(item!!,
              view!!.findNavController())
                || super.onOptionsItemSelected(item)

    }
 }


