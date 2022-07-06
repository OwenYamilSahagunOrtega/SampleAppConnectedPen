package com.dexcom.democonnectedpen.ui.dialogs.novodevices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.dexcom.democonnectedpen.ConnectedPenViewModel
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.DialogNovoDevicesAvailableBinding
import com.dexcom.insulinpen.models.InsulinPenDiscoveredDevice
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogNovoDevicesAvailable: BottomSheetDialogFragment() {

    private lateinit var binding: DialogNovoDevicesAvailableBinding
    private val viewModel: ConnectedPenViewModel by viewModel()
    private var alertDialog: AlertDialog? = null

    private val discoveredDevicesObserver = Observer<Pair<List<InsulinPenDiscoveredDevice>, Exception>> {
        val discoveredDevices = it.first
        Log.v("DEXCOM", "DialogNovoDevicesAvailable-discoveredDevicesLiveData: ${discoveredDevices.size}")
        if (discoveredDevices.isNotEmpty()) {
            val device = discoveredDevices.first()
            confirmConnectToPen(device)
        }

        val message: String? = it.second.message
        if (message != null) {
            Log.v("DEXCOM", "DialogNovoDevicesAvailable-message: $message")
            showError(message)
            popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNovoDevicesAvailableBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.discoveredDevicesLiveData.observe(viewLifecycleOwner, discoveredDevicesObserver)
    }

    override fun onDestroyView() {
        viewModel.discoveredDevicesLiveData.removeObserver(discoveredDevicesObserver)
        super.onDestroyView()
        Log.v("DEXCOM", "DialogNovoDevicesAvailable-onDestroyView")
    }

    private fun confirmConnectToPen(device: InsulinPenDiscoveredDevice) {
        alertDialog = this.let {
            val builder = AlertDialog.Builder(it.requireContext())
            builder.apply {
                setTitle("Connect to ${device.name}")
                setPositiveButton("Connect") { _, _ ->
                    binding.idDescriptionAvailableDevices.text = "Connecting to ${device.name}"
                    viewModel.onConnect(device = device)
                    alertDialog!!.dismiss()
                    popBackStack()
                }
                setNegativeButton("Cancel") { _, _ ->
                    alertDialog!!.dismiss()
                }
            }
            builder.create()
        }
        alertDialog!!.show()
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

    private fun popBackStack() {
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.popBackStack()
    }
}