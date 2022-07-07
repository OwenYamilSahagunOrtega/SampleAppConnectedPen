package com.dexcom.democonnectedpen.ui.fragments.peninformation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dexcom.democonnectedpen.databinding.FragmentPenInformationBinding
import com.dexcom.insulinpen.models.InsulinPenInfo
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPenInformation: Fragment() {

    private lateinit var binding: FragmentPenInformationBinding
    private val viewModel: FragmentPenInformationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPenInformationBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arguments?.getString(INSULIN_PEN_INFO)
        Log.v("DEXCOM", "FragmentPenInformation-data: $data")
        val insulinPenInfo: InsulinPenInfo = Gson().fromJson(data, InsulinPenInfo::class.java)

        super.onViewCreated(view, savedInstanceState)
        binding.penInformationTitle.text = "Name: ${insulinPenInfo.name}"
        binding.penInformationUuid.text = "UUID: ${insulinPenInfo.uuid}"
        binding.penInformationBattery.text = "Battery: ${insulinPenInfo.batteryStatus}"
        binding.penInformationManufacturer.text = "Manufacturer: ${insulinPenInfo.manufacturerName}"
        binding.penInformationLastsync.text = "LastSync: ${insulinPenInfo.lastSync}"
        binding.penInformationSoftwareRevision.text = "Software: ${insulinPenInfo.softwareRevision}"
        binding.penInformationSerialNumber.text = "Serial Number: ${insulinPenInfo.serialNumber}"
        binding.penInformationModelNumber.text = "Model: ${insulinPenInfo.modelNumber}"
        binding.penInformationHardwareRevision.text = "Hardware: ${insulinPenInfo.hardwareRevision}"
        binding.penInformationFirmwareRevision.text = "Firmware: ${insulinPenInfo.firmwareRevision}"
        binding.penInformationTitle.setOnClickListener {
            viewModel.toPrincipalScreen(findNavController())
        }
    }

    companion object {
        const val INSULIN_PEN_INFO = "insulinPenInfo"
    }
}