package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.navigation.database.DoubleDice
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.AllRollsBinding


class AllRollFragment : Fragment() {

    private lateinit var viewModel : OneDiceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: AllRollsBinding  = inflate(
                inflater, R.layout.all_rolls, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = OneDiceViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(OneDiceViewModel::class.java)

        val manager = GridLayoutManager(activity, 3)
        binding.rollList.layoutManager = manager

        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> 3
                else -> 1
            }
        }

        val adapter = DoubleDiceAdapter(DoubleDiceListener  { rollId ->
            Toast.makeText(context,"${rollId}",Toast.LENGTH_SHORT).show()
        })

        binding.rollList.adapter = adapter

        viewModel.rolls1.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }


}