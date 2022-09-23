package com.example.eatzers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatzers.adapters.Chips_Adapter
import com.example.eatzers.adapters.Menu_Adapter
import com.example.eatzers.databinding.ActivityHomeScreenBinding
import com.example.eatzers.models.Menu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeScreen : AppCompatActivity() {
    val firestore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        /** Recycler views - for category and menu **/
        val rcv_category = binding.rcvCategory
        val rcv_menu = binding.rcvMenu

        /** Lists for recycler view - for category and menu **/
        val menuList = ArrayList<Menu>()
        val categoryList = ArrayList<String>()

        /** Adapters - for category and menu **/
        val categoryListAdapter = Chips_Adapter(categoryList, this)
        val menuListAdapter = Menu_Adapter(menuList, this)

        /** Category list **/
        categoryList.add("Hot")
        categoryList.add("Chicken")
        categoryList.add("Curry")
        categoryList.add("Rice")
        categoryList.add("Fish")
        categoryList.add("Fruits")
        categoryList.add("Icecreams")
        categoryList.add("Soft Drinks")

        rcv_category.adapter = categoryListAdapter
        rcv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        /** Reading data from Firestore - food items **/
        firestore.collection("foodItems")
            .get().addOnSuccessListener{result->

                for (document in result.documents)
                {
                    menuList.add(Menu(document.data?.get("id").toString(),
                        document.data?.get("title").toString(),
                        document.data?.get("category").toString(),
                        document.data?.get("imageURL").toString(),
                        document.data?.get("price").toString()))
                }
                menuListAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener{exception->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }


        rcv_menu.adapter = menuListAdapter
        rcv_menu.layoutManager = GridLayoutManager(this, 2)

    }
}