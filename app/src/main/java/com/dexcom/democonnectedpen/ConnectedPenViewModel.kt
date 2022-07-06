package com.dexcom.democonnectedpen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dexcom.democonnectedpen.dexcom.SingleLiveEvent
import com.dexcom.insulinpen.controller.IBasePenController
import com.dexcom.insulinpen.mediator.IInsulinPenMediator
import com.dexcom.insulinpen.models.InsulinPenDiscoveredDevice
import com.dexcom.insulinpen.models.InsulinPenDose
import com.dexcom.insulinpen.models.InsulinPenManufacturer

class ConnectedPenViewModel(val insulinPenMediator: IInsulinPenMediator): ViewModel() {
    private var _discoveredDevicesLiveData = SingleLiveEvent<Pair<List<InsulinPenDiscoveredDevice>, Exception>>()
    private val _registeredDevicesLiveData = SingleLiveEvent<Pair<MutableMap<String, IBasePenController>, Exception>>()
    private val _insulinDosesLiveData = SingleLiveEvent<List<InsulinPenDose>>()
    private val _isScanning = SingleLiveEvent<Boolean>()
    private val _isConnecting = SingleLiveEvent<Boolean>()
    val discoveredDevicesLiveData: LiveData<Pair<List<InsulinPenDiscoveredDevice>, Exception>> = _discoveredDevicesLiveData
    val registeredDevicesLiveData: LiveData<Pair<MutableMap<String, IBasePenController>, Exception>> = _registeredDevicesLiveData
    val insulinDosesLiveData: LiveData<List<InsulinPenDose>> = _insulinDosesLiveData
    val isScanning: LiveData<Boolean> = _isScanning
    val isConnecting: LiveData<Boolean> = _isConnecting

    init {
        Log.v("DEXCOM", "ConnectedPenViewModel-init")
        insulinPenMediator.discoveredDevicesLiveData.observe(
            insulinPenMediator.owner, {
                Log.v("DEXCOM", "ConnectedPenViewModel-init2")
                //_discoveredDevicesLiveData.value = it
                _discoveredDevicesLiveData.postValue(it)
                _isScanning.value = false
            }
        )
        insulinPenMediator.registeredDevicesLiveData.observe(
            insulinPenMediator.owner, {
                //_registeredDevicesLiveData.value = it
                _registeredDevicesLiveData.postValue(it)
                _isConnecting.value = false
            }
        )
        insulinPenMediator.insulinDosesLiveData.observe(
            insulinPenMediator.owner, {
                //_insulinDosesLiveData.value = it
                _insulinDosesLiveData.postValue(it)
            }
        )
    }

    fun onClickStarScan(hrid: String = "", manufacturer: InsulinPenManufacturer) {
        _isScanning.value = true
        insulinPenMediator.startScanBy(
            manufacturer = manufacturer,
            hrid = hrid,
            timeout = 20.0
        )
    }

    fun onConnect(device: InsulinPenDiscoveredDevice) {
        _isConnecting.value = true
        insulinPenMediator.connect(
            discoveredDevice = device,
            timeout = 20.0
        )
    }
}