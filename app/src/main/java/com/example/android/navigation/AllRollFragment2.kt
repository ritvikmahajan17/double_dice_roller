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
import com.example.android.navigation.database.DoubleDiceDatabase
import com.example.android.navigation.databinding.AllRolls2Binding
import com.example.android.navigation.databinding.AllRollsBinding


class AllRollFragment2 : Fragment() {

    private lateinit var viewModel : TwoDiceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: AllRolls2Binding  = inflate(
                inflater, R.layout.all_rolls2, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DoubleDiceDatabase.getInstance(application).diceDao

        val viewModelFactory = TwoDiceViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(TwoDiceViewModel::class.java)

        val manager2 = GridLayoutManager(activity, 2)
        binding.rollList2.layoutManager = manager2

        manager2.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> 2
                else -> 1
            }
        }

        val adapter2 = DoubleDiceAdapter2(DoubleDiceListener2  { rollId ->
            Toast.makeText(context,"${rollId}",Toast.LENGTH_SHORT).show()
        })

        binding.rollList2.adapter = adapter2

        viewModel.rolls2.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter2.addHeaderAndSubmitList2(it)
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }


}