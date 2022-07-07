package com.dexcom.democonnectedpen.ui.fragments.peninformation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dexcom.democonnectedpen.R

class FragmentPenInformationViewModel: ViewModel() {

    fun remove(navController: NavController){
        navController.navigate(R.id.action_pen_information_to_connections)
    }

    fun toPrincipalScreen(navController: NavController){
        navController.navigate(R.id.action_pen_information_to_connections)
    }
}