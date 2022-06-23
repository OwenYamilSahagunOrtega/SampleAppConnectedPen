package com.dexcom.democonnectedpen.ui.lilly

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dexcom.insulinpen.controller.BasePenControllerImpl
import com.dexcom.insulinpen.mediator.InsulinPenMediator
import com.dexcom.insulinpen.models.InsulinPenDiscoveredDevice
import com.dexcom.insulinpen.models.InsulinPenManufacturer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch

class LillyViewModel(val insulinPenMediator: InsulinPenMediator) : ViewModel() {

    var discoveredDevices = MutableLiveData<List<InsulinPenDiscoveredDevice>>()
    var registeredDevices = MutableLiveData<MutableMap<String, BasePenControllerImpl>>()
    var showProgress = MutableLiveData<Boolean>()
    val manufacturer = InsulinPenManufacturer.Lilly

    fun onClickStarScan() {
        insulinPenMediator.startScanBy(
            manufacturer = manufacturer,
            timeout = 20.0
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                showProgress.postValue(insulinPenMediator.isScanning(manufacturer))
                while (insulinPenMediator.isScanning(manufacturer)) {
                    discoveredDevices.postValue(insulinPenMediator.discoveredDevicesChannel.receive())
                }
                showProgress.postValue(insulinPenMediator.isScanning(manufacturer))
            } catch (e: ClosedReceiveChannelException) { }
        }
    }

    fun onConnect(device: InsulinPenDiscoveredDevice) {
        if (insulinPenMediator.isScanning(manufacturer) || insulinPenMediator.isConnecting(manufacturer)) {
            return
        }
        insulinPenMediator.connect(
            discoveredDevice = device,
            timeout = 40.0
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                showProgress.postValue(insulinPenMediator.isConnecting(manufacturer))
                while (insulinPenMediator.isConnecting(manufacturer)) {
                    registeredDevices.postValue(insulinPenMediator.registeredDevicesChannel.receive())
                }
                showProgress.postValue(insulinPenMediator.isConnecting(manufacturer))
            } catch (e: ClosedReceiveChannelException) { }
        }
    }
}