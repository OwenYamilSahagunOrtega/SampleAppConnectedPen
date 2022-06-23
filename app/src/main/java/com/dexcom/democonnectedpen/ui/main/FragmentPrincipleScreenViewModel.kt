package com.dexcom.democonnectedpen

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.dexcom.insulinpen.models.InsulinPenInfo

class FragmentPrincipleScreenViewModel: ViewModel() {

    fun toDialogConnectNewPen(view: View){
        view.findNavController().navigate(R.id.action_principal_to_new_device)
    }

    fun toFragmentInformationPen(
        view: View,
        insulinPenInfo: InsulinPenInfo,
        navController: NavController
    ){
        val bundle = bundleOf(
            FragmentPenInformation.NAME_PEN to insulinPenInfo.name,
            FragmentPenInformation.UUID_PEN to insulinPenInfo.UUID,
            FragmentPenInformation.MANUFACTURER to insulinPenInfo.manufacturerName.toString(),
            FragmentPenInformation.LASTSYNC to insulinPenInfo.lastSync.toString(),
            FragmentPenInformation.SOFTWAREREVISION to insulinPenInfo.softwareRevision,
            FragmentPenInformation.SERIALNUMBER to insulinPenInfo.serialNumber,
            FragmentPenInformation.MODELNUMBER to insulinPenInfo.modelNumber,
            FragmentPenInformation.HARDWAREREVISION to insulinPenInfo.hardwareRevision,
            FragmentPenInformation.FIRMWAREREVISION to insulinPenInfo.firmwareRevision)
        navController.navigate(R.id.action_principal_to_information, bundle)
    }

}