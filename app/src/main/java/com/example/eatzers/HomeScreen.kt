package com.example.eatzers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatzers.adapters.Chips_Adapter
import com.example.eatzers.adapters.Menu_Adapter
import com.example.eatzers.adapters.OnChipClickListener
import com.example.eatzers.databinding.ActivityHomeScreenBinding
import com.example.eatzers.models.Menu
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class HomeScreen : AppCompatActivity(), OnChipClickListener {
    /** Firbase Instances **/
    val firestore = Firebase.firestore

    /** Variables **/
    lateinit var menuList:ArrayList<Menu>
    lateinit var categoryList:ArrayList<String>

    lateinit var menuListAdapter:Menu_Adapter
    lateinit var categoryListAdapter:Chips_Adapter

    var MAX_ITEMS:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        /** Recycler views - for category and menu **/
        val rcv_category = binding.rcvCategory
        val rcv_menu = binding.rcvMenu

        /** Lists for recycler view - for category and menu **/
        menuList = ArrayList<Menu>()
        categoryList = ArrayList<String>()

        /** Adapters - for category and menu **/
        categoryListAdapter = Chips_Adapter(categoryList, this, this)
        menuListAdapter = Menu_Adapter(menuList, this)

        /** Category list **/
        categoryList.add("All")
        categoryList.add("Chicken")
        categoryList.add("Curry")
        categoryList.add("Rice")
        categoryList.add("Fish")
        categoryList.add("Fruits")
        categoryList.add("Icecreams")
        categoryList.add("Drinks")

        rcv_category.adapter = categoryListAdapter
        rcv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        /** Reading data from Firestore - food items **/
        DefaultFoodItems()

        rcv_menu.adapter = menuListAdapter
        rcv_menu.layoutManager = GridLayoutManager(this, 2)

    }

    private fun DefaultFoodItems() {
        menuList.clear()
        firestore.collection("foodItems")
            .get().addOnSuccessListener{result->
                MAX_ITEMS = result.size()
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
    }

    override fun OnChipClick(text: String, isChecked: Boolean) {
        if(isChecked)
        {
            if(text == "All")
            {
                DefaultFoodItems()
                return
            }

            if(menuList.size == MAX_ITEMS)
                menuList.clear()

            /** Getting food items of specific category **/
            firestore.collection("foodItems")
                .get().addOnSuccessListener{result->

                    for (document in result.documents)
                    {
                        if(!(document.data?.get("category").toString().equals(text.replace(" ", ""), true)))
                            continue

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
        }
        else
        {
            /** Getting food items of specific category **/
            firestore.collection("foodItems")
                .get().addOnSuccessListener{result->

                    for (document in result.documents)
                    {
                        if(!(document.data?.get("category").toString().equals(text.replace(" ", ""), true)))
                            continue

                        val tempItem = Menu(document.data?.get("id").toString(),
                            document.data?.get("title").toString(),
                            document.data?.get("category").toString(),
                            document.data?.get("imageURL").toString(),
                            document.data?.get("price").toString())

                        menuList.remove(tempItem)
                    }

                    menuListAdapter.notifyDataSetChanged()

                }
                .addOnFailureListener{exception->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}