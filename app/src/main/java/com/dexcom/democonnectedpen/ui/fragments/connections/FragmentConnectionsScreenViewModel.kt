package com.dexcom.democonnectedpen.ui.fragments.connections

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.dexcom.democonnectedpen.ui.fragments.peninformation.FragmentPenInformation
import com.dexcom.democonnectedpen.R
import com.dexcom.insulinpen.models.InsulinPenInfo
import com.dexcom.insulinpen.models.InsulinPenManufacturer
import com.google.gson.Gson

class FragmentConnectionsScreenViewModel(): ViewModel() {

    fun toDialogConnectNewPen(view: View, manufacturer: InsulinPenManufacturer){
        if (manufacturer == InsulinPenManufacturer.Lilly) {
            view.findNavController().navigate(R.id.action_connections_to_lilly_devices)
        } else if (manufacturer == InsulinPenManufacturer.Novo) {
            view.findNavController().navigate(R.id.action_connections_to_novo_devices)
        }
    }

    fun toFragmentInformationPen(
        insulinPenInfo: InsulinPenInfo,
        navController: NavController
    ) {
        val penInfoJson: String? = try {
            Gson().toJson(insulinPenInfo).toString()
        } catch (e: Exception) {
            null
        }
        Log.v("DEXCOM", "toFragmentInformationPen-penInfoJson: $penInfoJson")

        val bundle = bundleOf(
            FragmentPenInformation.INSULIN_PEN_INFO to penInfoJson
        )
        navController.navigate(R.id.action_connections_to_pen_information, bundle)
    }
}