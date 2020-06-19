package com.example.android.navigation

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.android.navigation.database.DoubleDice
import com.example.android.navigation.databinding.OneDiceFragmentBinding



@BindingAdapter("DiceImage")
fun ImageView.setDiceImage(item: DoubleDice?) {
    item?.let {
        if (it.rollValue_mode1 != null){
        setImageResource(when (it.rollValue_mode1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        })
        }
        else
            setImageResource(R.drawable.ic_baseline_trending_flat_24)
    }
}

@BindingAdapter("Dice_value_text_color")
fun TextView.setDiceValueTextColor(item: DoubleDice?) {
    item?.let {
        if(it.rollValue_mode1 == 6)
            setTextColor(Color.RED)
        else
            setTextColor(Color.BLACK)
    }
}

@BindingAdapter("Dice_value_text")
fun TextView.setDiceValueText(item: DoubleDice?) {
    item?.let {
            if(it.rollValue_mode1 != null) {
                text = it.rollValue_mode1.toString()
            }

    }
}

