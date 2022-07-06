package com.dexcom.democonnectedpen.ui.fragments.doses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dexcom.democonnectedpen.R
import com.dexcom.democonnectedpen.databinding.RowDoseBinding
import com.dexcom.insulinpen.models.InsulinPenDose
import com.dexcom.insulinpen.models.InsulinPenManufacturer

class AdapterDosesScreen(
    private val onClickListener:(InsulinPenDose)->Unit
): RecyclerView.Adapter<AdapterDosesScreen.AdapterDosesViewHolder>() {
    private var penDoses: List<InsulinPenDose> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setPenDoses(doses: List<InsulinPenDose>) {
        penDoses = doses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDosesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AdapterDosesViewHolder(layoutInflater.inflate(R.layout.row_dose,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterDosesViewHolder, position: Int) {
        val item = penDoses[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return penDoses.size
    }

    class AdapterDosesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RowDoseBinding.bind(view)

        fun render(insulinPenDose: InsulinPenDose, onClickListener: (InsulinPenDose) -> Unit){
            binding.idDoseId.text = "Id: ${insulinPenDose.doseID}, Device: ${insulinPenDose.deviceModel}"
            binding.idDoseDate.text = "Type: ${insulinPenDose.actionType}, Date: ${insulinPenDose.doseTime}"
            when (insulinPenDose.deviceManufacturer) {
                InsulinPenManufacturer.Novo.value -> {
                    binding.idDoseIcon.setImageResource(R.drawable.ic_novo)
                }
                InsulinPenManufacturer.Lilly.value -> {
                    binding.idDoseIcon.setImageResource(R.drawable.ic_lilly)
                }
                else -> {
                    binding.idDoseIcon.setImageResource(0)
                }
            }
            itemView.setOnClickListener {
                (onClickListener(insulinPenDose))
            }
        }
    }
}