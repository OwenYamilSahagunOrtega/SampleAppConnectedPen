package com.dexcom.democonnectedpen.ui.lilly

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dexcom.democonnectedpen.DialogFragmentDevicesAvailableViewModel
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.ItemPenListBinding
import com.dexcom.democonnectedpen.pendata.PenDataProvider
import com.dexcom.insulinpen.models.InsulinPenDiscoveredDevice
import com.dexcom.insulinpen.models.InsulinPenInfo

class LillyAdapter(
    val clickListener: OnClickListener
) : RecyclerView.Adapter<LillyAdapter.LillyViewHolder>() {
    private var discoveredDevices: List<InsulinPenDiscoveredDevice> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setDevices(devices: List<InsulinPenDiscoveredDevice>) {
        discoveredDevices = devices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LillyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPenListBinding.inflate(inflater, parent, false)
        return LillyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LillyViewHolder, position: Int) {
        val device = discoveredDevices[position]
        holder.binding.idPenAvaibleName.text = device.name
        holder.binding.idPenAvaibleDescription.text = device.uuid

        holder.binding.root.setOnClickListener {
            clickListener.onClick(device)

        }
    }

    override fun getItemCount(): Int {
        return discoveredDevices.size
    }

    class LillyViewHolder(val binding: ItemPenListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    class OnClickListener(val clickListener: (device: InsulinPenDiscoveredDevice) -> Unit) {
        fun onClick(device: InsulinPenDiscoveredDevice) = clickListener(device)

    }

}