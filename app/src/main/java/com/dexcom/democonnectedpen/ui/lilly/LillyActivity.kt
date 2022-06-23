package com.dexcom.democonnectedpen.ui.lilly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.ActivityLillyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LillyActivity : AppCompatActivity() {
    private val lillyViewModel: LillyViewModel by viewModel()


//    private val adapter = LillyAdapter(LillyAdapter.OnClickListener {
//        Log.v("DEXCOM", "name: ${it.name}")
//        lillyViewModel.onConnect(device = it)
//    }, findNavController(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        //setupLiveData()
    }

    private fun setupView() {
        val binding = ActivityLillyBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@LillyActivity
            vm = lillyViewModel
        }
        setContentView(binding.root)
        //binding.recyclerview.adapter = adapter
    }
//
//    private fun setupLiveData() {
//        lillyViewModel.discoveredDevices.observe(this, {
//            Log.v("DEXCOM", "discoveredDevicesCollect: ${it.size}")
//            if (it.size != adapter.itemCount) {
//                adapter.setDevices(it)
//            }
//        })
//        lillyViewModel.registeredDevices.observe(this, {
//            Log.v("DEXCOM", "registeredDevicesCollect: ${it.size}")
//            it.forEach { (uuid, controller) ->
//                Log.v("DEXCOM", "uuid: $uuid, controller: ${controller.info}")
//            }
//        })
//    }
}