package com.dexcom.democonnectedpen.ui.dialogs.lillydevices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dexcom.democonnectedpen.ConnectedPenViewModel
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.DialogLillyDevicesAvailableBinding
import com.dexcom.insulinpen.models.InsulinPenDiscoveredDevice
import com.dexcom.insulinpen.models.InsulinPenManufacturer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogLillyDevicesAvailable: BottomSheetDialogFragment() {

    private lateinit var binding: DialogLillyDevicesAvailableBinding
    private val viewModel: ConnectedPenViewModel by viewModel()
    private var alertDialog: AlertDialog? = null

    private val adapterLillyDevices = AdapterDialogLillyDevices(AdapterDialogLillyDevices.OnClickListener {
        confirmConnectToPen(it)
    })

    private val discoveredDevicesObserver = Observer<Pair<List<InsulinPenDiscoveredDevice>, Exception>> {
        val discoveredDevices = it.first
        Log.v("DEXCOM", "DialogLillyDevicesAvailable-discoveredDevicesLiveData: ${discoveredDevices.size}")
        adapterLillyDevices.setDiscoveredDevices(discoveredDevices)

        val message: String? = it.second.message
        if (message != null) {
            Log.v("DEXCOM", "DialogLillyDevicesAvailable-message: $message")
            showError(message)
            popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLillyDevicesAvailableBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerDevicesList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterLillyDevices
        }

        viewModel.discoveredDevicesLiveData.observe(viewLifecycleOwner, discoveredDevicesObserver)
        viewModel.onClickStarScan(manufacturer = InsulinPenManufacturer.Lilly)
    }

    override fun onDestroyView() {
        viewModel.discoveredDevicesLiveData.removeObserver(discoveredDevicesObserver)
        super.onDestroyView()
        Log.v("DEXCOM", "DialogLillyDevicesAvailable-onDestroyView")
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