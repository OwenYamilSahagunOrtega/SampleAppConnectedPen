package com.dexcom.democonnectedpen

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dexcom.democonnectedpen.pendata.PenDataProvider
import com.dexcom.insulinpen.models.InsulinPenInfo

class DialogFragmentDevicesAvailableViewModel: ViewModel() {

    fun toFragmentPrincipleScreen(
        view: View,
        navController: NavController
    ){
        navController.navigate(R.id.action_new_device_to_principal)
    }
}