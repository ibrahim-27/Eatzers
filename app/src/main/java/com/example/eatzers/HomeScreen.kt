package com.example.eatzers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatzers.adapters.Chips_Adapter
import com.example.eatzers.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        /** Category list **/
        val categoryList = ArrayList<String>()
        categoryList.add("Chicken")
        categoryList.add("Curry")
        categoryList.add("Rice")
        categoryList.add("Fish")
        categoryList.add("Fruits")
        categoryList.add("Icecreams")
        categoryList.add("Soft Drinks")
        val categoryListAdapter = Chips_Adapter(categoryList, this)

        val rcv_category = binding.rcvCategory
        rcv_category.adapter = categoryListAdapter
        rcv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}