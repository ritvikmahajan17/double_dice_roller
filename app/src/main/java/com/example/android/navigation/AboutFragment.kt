package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.AboutFragmentBinding
import com.example.android.navigation.databinding.ActivityMainBinding
import com.example.android.navigation.databinding.HomePageBinding

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: AboutFragmentBinding = DataBindingUtil.inflate<AboutFragmentBinding>(inflater, R.layout.about_fragment, container, false)
        return binding.root
    }

}
