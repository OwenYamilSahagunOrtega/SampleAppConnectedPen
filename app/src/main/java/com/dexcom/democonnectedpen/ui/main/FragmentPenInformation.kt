package com.dexcom.democonnectedpen

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization.TITLE
import android.provider.MediaStore.Images.ImageColumns.DESCRIPTION
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.dexcom.democonnectedpen.databinding.FragmentPenInformationBinding
import com.dexcom.insulinpen.models.InsulinPenInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPenInformation: Fragment() {

    private lateinit var binding: FragmentPenInformationBinding
    private val viewmodel: FragmentPenInformationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPenInformationBinding.inflate(inflater).apply {
            vm = viewmodel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val name = arguments?.getString(NAME_PEN)
        val UUID = arguments?.getString(UUID_PEN)
        val manufacturer = arguments?.getString(MANUFACTURER)
        val lastsync = arguments?.getString(LASTSYNC)
        val softwarerevision = arguments?.getString(SOFTWAREREVISION)
        val serialnumber = arguments?.getString(SERIALNUMBER)
        val modelnumber = arguments?.getString(MODELNUMBER)
        val hardwarerevision = arguments?.getString(HARDWAREREVISION)
        val firmwarerevision = arguments?.getString(FIRMWAREREVISION)

        super.onViewCreated(view, savedInstanceState)
        binding.penInformationTitle.text = "Name: " + name
        binding.penInformationUuid.text = "UUID: " + UUID
        binding.penInformationManufacturer.text = "Manufacturer: " + manufacturer
        binding.penInformationLastsync.text = "LastSync: " + lastsync
        binding.penInformationSoftwarerevision.text = "Software: " + softwarerevision
        binding.penInformationSerialnumber.text = "Serial Number: " + serialnumber
        binding.penInformationModelnumber.text = "Model: " + modelnumber
        binding.penInformationHardwarerevision.text = "Hardware: " + hardwarerevision
        binding.penInformationFirmwarerevision.text = "Firmware: " + firmwarerevision
        binding.penInformationTitle.setOnClickListener {
            viewmodel.toPrincipalSreen(view, findNavController())
        }
    }

    companion object {
        const val NAME_PEN = "name"
        const val UUID_PEN = "UUID"
        const val MANUFACTURER = "manufacturerName"
        const val LASTSYNC = "lastSync"
        const val SOFTWAREREVISION = "softwareRevision"
        const val SERIALNUMBER = "serialNumber"
        const val MODELNUMBER = "modelNumber"
        const val HARDWAREREVISION = "hardwareRevision"
        const val FIRMWAREREVISION = "firmwareRevision"

    }
}