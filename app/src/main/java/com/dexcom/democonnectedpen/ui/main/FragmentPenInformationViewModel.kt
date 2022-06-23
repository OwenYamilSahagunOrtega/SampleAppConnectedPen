package com.dexcom.democonnectedpen

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class FragmentPenInformationViewModel: ViewModel() {

    fun toPrincipalSreen(view: View, navController: NavController){
        navController.navigate(R.id.action_information_to_principal)
    }


}