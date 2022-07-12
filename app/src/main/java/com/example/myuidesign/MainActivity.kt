package com.example.myuidesign

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.myuidesign.fragment.HomeFragment
import com.example.myuidesign.fragment.StockFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(){

    lateinit var curvedBottomNavigation: CurvedBottomNavigation
    lateinit var fabbtn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.P){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            fabbtn=findViewById(R.id.fabStockDetails)

            val stockFragment=StockFragment()
            val homeFragment=HomeFragment()
            setUpNavigation(homeFragment)

            fabbtn.setOnClickListener {
                val intent=Intent(applicationContext,DetailActivity::class.java)
                startActivity(intent)
            }

//        floatingActionButton=findViewById(R.id.fabStockDetails)
        curvedBottomNavigation=findViewById(R.id.navigationBar)
//         floatingActionButton.setOnClickListener {
//             val intent=Intent(applicationContext,DetailActivity::class.java)
//             startActivity(intent)
//
//         }

            curvedBottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home->{
                        setUpNavigation(homeFragment)

                    }
                    R.id.stockDetails->{
                        setUpNavigation(stockFragment)

                    }
                }
                true
            }
        }
    }

    private fun setUpNavigation(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment,fragment)
            addToBackStack(null)
            commit()
           
        }
    }
}