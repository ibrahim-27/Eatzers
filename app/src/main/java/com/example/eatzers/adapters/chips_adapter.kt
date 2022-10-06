package com.example.eatzers.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eatzers.R
import com.google.android.material.chip.Chip
import com.google.android.material.resources.MaterialResources.getColorStateList

class Chips_Adapter(var categoryList: List<String>, var context:Context, var onclick: OnChipClickListener): RecyclerView.Adapter<Chips_Adapter.ChipsViewHolder>() {

    lateinit var MAIN_CHIP:Chip     // to store the "All" chip so it can be unchecked when other chips are checked.

    class ChipsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var chip = itemView.findViewById<Chip>(R.id.chip_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rcv_chips_sample, parent, false)
        return ChipsViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ChipsViewHolder, position: Int) {
        holder.chip.text = categoryList[position]

        if(holder.chip.text.toString() == "All")
        {
            holder.chip.isChecked = true
            MAIN_CHIP = holder.chip
        }

        holder.chip.setOnCheckedChangeListener { chip, isChecked ->
            if(chip.text != "All")
                MAIN_CHIP.isChecked = false
            onclick.OnChipClick(chip.text.toString(), isChecked)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

interface OnChipClickListener
{
    fun OnChipClick(text:String, isChecked:Boolean)
}