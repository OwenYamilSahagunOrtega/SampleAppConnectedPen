package com.dexcom.democonnectedpen.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.Navigation
import com.dexcom.democonnectedpen.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.NavigationUI

import com.dexcom.democonnectedpen.R

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var selectedItem = R.id.id_menu_pens;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        NavigationUI.setupWithNavController(
            binding.bottomNavFragmentContentMain,
            navController
        )
        binding.bottomNavFragmentContentMain.setOnNavigationItemSelectedListener {
            Log.v("DEXCOM", "FragmentConnectionsScreen-item: ${it.title}")
            if (it.itemId == R.id.id_menu_pens) {
                if (selectedItem != R.id.id_menu_pens) {
                    selectedItem = R.id.id_menu_pens;
                    navController.popBackStack()
                }
            } else if (it.itemId == R.id.id_menu_doses) {
                if (selectedItem != R.id.id_menu_doses) {
                    selectedItem = R.id.id_menu_doses;
                    navController.navigate(R.id.id_doses_fragment);
                }
            }
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.v("DEXCOM", "FragmentConnectionsScreen-item: ${item.title}")
        return true
    }
}