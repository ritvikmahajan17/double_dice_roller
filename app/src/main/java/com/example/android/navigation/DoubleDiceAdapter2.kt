package com.example.android.navigation

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.database.DoubleDice
import com.example.android.navigation.databinding.LisItemRollValue2Binding
import com.example.android.navigation.databinding.ListItemRollValueBinding
import com.example.android.navigation.generated.callback.OnClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM2 = 2

class DoubleDiceAdapter2 (val clickListener: DoubleDiceListener2) :androidx.recyclerview.widget.ListAdapter<DataItem2, RecyclerView.ViewHolder>(DoubleDiceDiffCallBack2()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem2.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem2.DoubleDiceItem2 -> ITEM_VIEW_TYPE_ITEM2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder2 -> {
                val rollItem:DataItem2.DoubleDiceItem2 = getItem(position) as DataItem2.DoubleDiceItem2
                holder.bind(clickListener, rollItem.roll)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM2 -> ViewHolder2.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }


    fun addHeaderAndSubmitList2(list: List<DoubleDice>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem2.Header)
                else -> listOf(DataItem2.Header) + list.map { DataItem2.DoubleDiceItem2(it)}
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}

class ViewHolder2 private constructor(val binding: LisItemRollValue2Binding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: DoubleDiceListener2, item: DoubleDice) {
        binding.dice = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder2 {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =LisItemRollValue2Binding.inflate(layoutInflater, parent, false)
            return ViewHolder2(binding)
        }
    }
}

class DoubleDiceDiffCallBack2 : DiffUtil.ItemCallback<DataItem2>() {
    override fun areItemsTheSame(oldItem: DataItem2, newItem: DataItem2): Boolean {
        return oldItem.rollValue == newItem.rollValue
    }

    override fun areContentsTheSame(oldItem: DataItem2, newItem: DataItem2): Boolean {
        return oldItem == newItem
    }
}

class DoubleDiceListener2(val clickListener: (rollValue: Int?) -> Unit) {        // in this func, we have passed a certain night using onclick func and in clickListener
    fun onClick(roll: DoubleDice) = clickListener(roll.rollValue_mode2)       // we have passed that night's nightId as parameter
}

sealed class DataItem2 {

    data class DoubleDiceItem2(val roll: DoubleDice) : DataItem2() {
        override val rollValue = roll.rollValue_mode2
    }

    object Header : DataItem2() {
        override val rollValue = Int.MIN_VALUE
    }

    abstract val rollValue: Int?
}

