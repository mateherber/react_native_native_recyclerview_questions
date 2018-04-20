package com.dynamicnativerecyclerviewsample

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.info

class NativeListAdapter :
    ListAdapter<Pair<Int, Int>, NativeListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Pair<Int, Int>>() {
        override fun areItemsTheSame(oldItem: Pair<Int, Int>, newItem: Pair<Int, Int>) = oldItem === newItem
        override fun areContentsTheSame(oldItem: Pair<Int, Int>, newItem: Pair<Int, Int>) = oldItem == newItem
    }), AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NativeListAdapter.ViewHolder {
        val view = AnkoContext.create(parent.context, parent).run {
            frameLayout {
                val margin = dip(10)
                layoutParams = LinearLayout.LayoutParams(dip(100), WRAP_CONTENT).apply {
                    setMargins(margin, margin, margin, margin)
                }
            }
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NativeListAdapter.ViewHolder, position: Int) {
        val (columnHeight, color) = getItem(position)
        holder.itemView.apply {
            setOnClickListener {
                NativeListRepository.changeItem(position)
            }
            backgroundColor = color
            info { "Setting up ${(position + 1).ordinal} item with height $columnHeight" }
            layoutParams = layoutParams.apply {
                height = dip(columnHeight)
            }
        }

    }

    private val Int.ordinal
        get() = "${this}" + if (this % 100 in 11..13) "th" else when (this % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}