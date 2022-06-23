package com.dexcom.democonnectedpen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dexcom.democonnectedpen.databinding.ActivityLillyBinding
import com.dexcom.democonnectedpen.databinding.DialogDevicesAvailableBinding
import com.dexcom.democonnectedpen.pendata.PenDataProvider
import com.dexcom.democonnectedpen.ui.lilly.LillyAdapter
import com.dexcom.democonnectedpen.ui.lilly.LillyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogFragmentDevicesAvailable: BottomSheetDialogFragment() {

    private lateinit var binding: DialogDevicesAvailableBinding
    val viewmodel: LillyViewModel by viewModel()

    private val adapterLilly = LillyAdapter(LillyAdapter.OnClickListener {
        Log.v("DEXCOM", "name: ${it.name}")
        viewmodel.onConnect(device = it)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDevicesAvailableBinding.inflate(inflater).apply {
            vm = viewmodel
            lifecycleOwner = viewLifecycleOwner
        }

        val view = binding.root
        setupLiveData()
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.recyclerDevicesList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterLilly
        }

        viewmodel.onClickStarScan()
    }


    private fun setupLiveData() {

        val navHostFragment = activity?.
        supportFragmentManager?.
        findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        val navController = navHostFragment.navController

        viewmodel.discoveredDevices.observe(this, {
            Log.e("DEXCOM", "discoveredDevicesCollect: ${it.size}")
            if (it.size != adapterLilly.itemCount) {
                adapterLilly.setDevices(it)
            }
        })
        viewmodel.registeredDevices.observe(this, {
            Log.v("DEXCOM", "registeredDevicesCollect: ${it.size}")
            it.forEach { (uuid, controller) ->
                Log.e("DEXCOM", "uuid: $uuid, controller: ${controller.info}")
                PenDataProvider.PenDataList.add(controller.info)
            }
            navController.navigate(R.id.action_new_device_to_principal)
        })
    }
}