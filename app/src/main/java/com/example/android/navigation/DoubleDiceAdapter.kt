package com.example.android.navigation

import android.renderscript.ScriptGroup
import android.util.Log
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
private val ITEM_VIEW_TYPE_ITEM = 1

class DoubleDiceAdapter (val clickListener: DoubleDiceListener) :androidx.recyclerview.widget.ListAdapter<DataItem, RecyclerView.ViewHolder>(DoubleDiceDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.DoubleDiceItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val rollItem = getItem(position) as DataItem.DoubleDiceItem
                    holder.bind(clickListener, rollItem.roll)

             }
         }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }


    fun addHeaderAndSubmitList(list: List<DoubleDice>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.DoubleDiceItem(it)}
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}


    class ViewHolder private constructor(val binding: ListItemRollValueBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: DoubleDiceListener, item: DoubleDice) {
            binding.dice = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ListItemRollValueBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)

            }
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class DoubleDiceDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.rollValue == newItem.rollValue
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    class DoubleDiceListener(val clickListener: (rollValue: Int?) -> Unit) {        // in this func, we have passed a certain night using onclick func and in clickListener
        fun onClick(roll: DoubleDice) = clickListener(roll.rollValue_mode1)       // we have passed that night's nightId as parameter
    }

    sealed class DataItem {
        data class DoubleDiceItem(val roll: DoubleDice) : DataItem() {
            override val rollValue = roll.rollValue_mode1
        }

        object Header : DataItem() {
            override val rollValue = Int.MIN_VALUE
        }

        abstract val rollValue: Int?
    }

