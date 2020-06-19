package com.example.android.navigation

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.navigation.database.DoubleDice

        @BindingAdapter("DiceImage1")
    fun ImageView.setDiceImage1(item: DoubleDice?) {
        item?.let {
            if (it.rollValue_mode2 != null) {
                setImageResource(when (it.rollValue_mode2_d1) {
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

         @BindingAdapter("DiceImage2")
            fun ImageView.setDiceImage2(item: DoubleDice?) {
                item?.let {
                    if (it.rollValue_mode2 != null) {
                        setImageResource(when (it.rollValue_mode2_d2) {
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

    @BindingAdapter("Dice_value_text_color2")
    fun TextView.setDiceValueTextColor2(item: DoubleDice?) {
        item?.let {
            if(it.rollValue_mode2 == 12)
                setTextColor(Color.RED)
            else
                setTextColor(Color.BLACK)
        }
    }

    @BindingAdapter("Dice_value_text2")
    fun TextView.setDiceValueText2(item: DoubleDice?) {
        item?.let {
            if (it.rollValue_mode2 != null) {
                text = it.rollValue_mode2.toString()
            }
        }
    }