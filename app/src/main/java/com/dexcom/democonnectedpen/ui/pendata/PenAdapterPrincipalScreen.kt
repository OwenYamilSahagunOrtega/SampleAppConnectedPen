package com.dexcom.democonnectedpen.ui.pendata

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.ItemPenListBinding
import com.dexcom.insulinpen.models.InsulinPenInfo

class PenAdapterPrincipalScreen(val penList: List<InsulinPenInfo>,
    private val onClickListener:(InsulinPenInfo)->Unit):
    RecyclerView.Adapter<PenAdapterPrincipalScreen.PenAdapterViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return PenAdapterViewHolder(layoutInflater.inflate(R.layout.item_pen_list,parent,false))
        }

        override fun onBindViewHolder(holder: PenAdapterViewHolder, position: Int) {
            val item = penList[position]
            holder.render(item, onClickListener)
        }

        override fun getItemCount(): Int {
            return penList.size
        }

        class PenAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {

            val binding = ItemPenListBinding.bind(view)

            fun render(insulinPenInfo: InsulinPenInfo,
                       onClickListener: (InsulinPenInfo) -> Unit){
                binding.idPenAvaibleName.text = insulinPenInfo.name
                binding.idPenAvaibleDescription.text = insulinPenInfo.UUID
                itemView.setOnClickListener {
                    (onClickListener(insulinPenInfo))
                }
            }

        }
    }