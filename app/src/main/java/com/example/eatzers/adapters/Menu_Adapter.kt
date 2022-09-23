package com.example.eatzers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatzers.R
import com.example.eatzers.models.Menu
import de.hdodenhof.circleimageview.CircleImageView

class Menu_Adapter(var menuList: List<Menu>, var context: Context) : RecyclerView.Adapter<Menu_Adapter.MenuViewHolder>() {
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var tv_foodName = itemView.findViewById<TextView>(R.id.tv_foodName)
        var tv_foodCategory = itemView.findViewById<TextView>(R.id.tv_foodCategory)
        var iv_foodImage = itemView.findViewById<CircleImageView>(R.id.iv_foodImage)
        var tv_foodPrice = itemView.findViewById<TextView>(R.id.tv_foodPrice)
        var btn_inc = itemView.findViewById<ImageButton>(R.id.btn_inc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rcv_menulist_sample, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.apply {
            tv_foodName.text = menuList[position].foodName
            tv_foodCategory.text = menuList[position].foodCategory
            Glide.with(context).load(menuList[position].foodImageURL).into(iv_foodImage);
            tv_foodPrice.text = "$${menuList[position].foodPrice}"

            btn_inc.setOnClickListener(){

            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}