package com.dexcom.democonnectedpen.ui.fragments.connections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dexcom.democonnectedpen.ConnectedPenViewModel
import com.dexcom.democonnectedpen.databinding.FragmentConnectionsScreenBinding
import com.dexcom.democonnectedpen.pendata.PenDataProvider
import com.dexcom.insulinpen.controller.IBasePenController
import com.dexcom.insulinpen.models.InsulinPenInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentConnectionsScreen: Fragment() {

    private lateinit var binding: FragmentConnectionsScreenBinding
    private val connectedPenViewModel: ConnectedPenViewModel by viewModel()
    private val connectionsViewModel: FragmentConnectionsScreenViewModel by viewModel()

    private val adapterConnections = AdapterConnectionsScreen {
        connectionsViewModel.toFragmentInformationPen(it, findNavController())
    }

    private val registeredDevicesObserver = Observer<Pair<MutableMap<String, IBasePenController>, Exception>> {
        val registeredDevices = it.first
        Log.v("DEXCOM", "DialogNovoDevicesAvailable-registeredDevices: ${registeredDevices.size}")
        if (registeredDevices.isNotEmpty()) {
            val isAddInsulinPenInfo = PenDataProvider.setInsulinPenInfoList(registeredDevices)
            if (isAddInsulinPenInfo) {
                PenDataProvider.setInsulinPenInfoList(registeredDevices)
                val devices: List<InsulinPenInfo> = PenDataProvider.getInsulinPenInfoList().map { device -> device.info }.toList()
                adapterConnections.setRegisteredDevices(devices)
            }
            binding.idPenNoConnected.visibility = if (PenDataProvider.getInsulinPenInfoList().isEmpty()) { View.VISIBLE } else { View.GONE }
        } else {
            val message: String? = it.second.message
            if (message != null) {
                showError(message)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectionsScreenBinding.inflate(inflater).apply {
            vm = connectionsViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.idDevicesConnected.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterConnections
        }

        val devices: List<InsulinPenInfo> = PenDataProvider.getInsulinPenInfoList().map { it.info }.toList()
        adapterConnections.setRegisteredDevices(devices)
        connectedPenViewModel.registeredDevicesLiveData.observe(viewLifecycleOwner, registeredDevicesObserver)
    }

    override fun onDestroyView() {
        connectedPenViewModel.registeredDevicesLiveData.removeObserver(registeredDevicesObserver)
        super.onDestroyView()
        Log.v("DEXCOM", "FragmentConnectionsScreen-onDestroyView")
    }

    private fun showError(message: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}