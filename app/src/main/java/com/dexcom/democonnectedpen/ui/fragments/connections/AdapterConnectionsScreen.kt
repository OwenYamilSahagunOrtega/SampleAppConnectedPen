package com.dexcom.democonnectedpen.ui.fragments.connections

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.RowConnectionsBinding
import com.dexcom.insulinpen.models.InsulinPenInfo
import com.dexcom.insulinpen.models.InsulinPenManufacturer

class AdapterConnectionsScreen(
    private val onClickListener:(InsulinPenInfo)->Unit
): RecyclerView.Adapter<AdapterConnectionsScreen.AdapterConnectionsViewHolder>() {
    private var registeredDevices: List<InsulinPenInfo> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setRegisteredDevices(devices: List<InsulinPenInfo>) {
        registeredDevices = devices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterConnectionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AdapterConnectionsViewHolder(layoutInflater.inflate(R.layout.row_connections,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterConnectionsViewHolder, position: Int) {
        val item = registeredDevices[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return registeredDevices.size
    }

    class AdapterConnectionsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RowConnectionsBinding.bind(view)

        fun render(insulinPenInfo: InsulinPenInfo, onClickListener: (InsulinPenInfo) -> Unit){
            binding.idConnectionsName.text = insulinPenInfo.name
            binding.idConnectionsUuid.text = insulinPenInfo.uuid
            when (insulinPenInfo.manufacturerName) {
                InsulinPenManufacturer.Novo -> {
                    binding.idPenAvaibleIcon.setImageResource(R.drawable.ic_novo)
                }
                InsulinPenManufacturer.Lilly -> {
                    binding.idPenAvaibleIcon.setImageResource(R.drawable.ic_lilly)
                }
                else -> {
                    binding.idPenAvaibleIcon.setImageResource(0)
                }
            }

            itemView.setOnClickListener {
                (onClickListener(insulinPenInfo))
            }
        }
    }
}