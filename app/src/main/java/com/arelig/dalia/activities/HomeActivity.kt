package com.arelig.dalia.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arelig.dalia.R
import com.arelig.dalia.fragments.AddPlantFragment
import com.arelig.dalia.fragments.MyPlantsFragment
import com.arelig.dalia.fragments.TodayFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        startViews()
    }

    private fun startViews() {
        loadFragment(MyPlantsFragment())

        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.op_my_plants -> {
                    loadFragment(MyPlantsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.op_add_plant -> {
                    loadFragment(AddPlantFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.op_today -> {
                    loadFragment(TodayFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }
    }

}


