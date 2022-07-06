package com.dexcom.democonnectedpen.ui.dialogs.lillydevices

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dexcom.democonnectedpen.databinding.RowLillyDeviceBinding
import com.dexcom.insulinpen.models.InsulinPenDiscoveredDevice

class AdapterDialogLillyDevices(
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<AdapterDialogLillyDevices.AdapterDialogLillyDevices>() {
    private var discoveredDevices: List<InsulinPenDiscoveredDevice> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setDiscoveredDevices(devices: List<InsulinPenDiscoveredDevice>) {
        discoveredDevices = devices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDialogLillyDevices {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowLillyDeviceBinding.inflate(inflater, parent, false)
        return AdapterDialogLillyDevices(binding)
    }

    override fun onBindViewHolder(holder: AdapterDialogLillyDevices, position: Int) {
        val device = discoveredDevices[position]
        holder.binding.idLillyDeviceTitle.text = device.name
        holder.binding.idLillyDeviceUuid.text = device.uuid

        holder.binding.root.setOnClickListener {
            clickListener.onClick(device)

        }
    }

    override fun getItemCount(): Int {
        return discoveredDevices.size
    }

    class AdapterDialogLillyDevices(val binding: RowLillyDeviceBinding) : RecyclerView.ViewHolder(binding.root)

    class OnClickListener(val clickListener: (device: InsulinPenDiscoveredDevice) -> Unit) {
        fun onClick(device: InsulinPenDiscoveredDevice) = clickListener(device)
    }
}