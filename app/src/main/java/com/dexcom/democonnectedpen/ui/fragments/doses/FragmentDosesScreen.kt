package com.dexcom.democonnectedpen.ui.fragments.doses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dexcom.democonnectedpen.ConnectedPenViewModel
import com.dexcom.democonnectedpen.databinding.FragmentDosesScreenBinding
import com.dexcom.democonnectedpen.pendata.PenDataProvider
import com.dexcom.insulinpen.models.InsulinPenDose
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentDosesScreen: Fragment() {

    private lateinit var binding: FragmentDosesScreenBinding
    private val viewModel: ConnectedPenViewModel by viewModel()

    private val adapterDoses = AdapterDosesScreen {
        Log.v("DEXCOM", "AdapterDosesScreen: $it")
    }

    private val insulinDosesObserver = Observer<List<InsulinPenDose>> {
        PenDataProvider.setInsulinPenDoseList(it)
        val insulinDoses = PenDataProvider.getInsulinPenDoseList().sortedByDescending { dose -> dose.doseTime }
        Log.v("DEXCOM", "FragmentDoses-insulinDosesLiveData: ${insulinDoses.size}")
        adapterDoses.setPenDoses(insulinDoses)
        binding.idDosesNoHave.visibility = if (insulinDoses.isEmpty()) { View.VISIBLE } else { View.GONE }
        binding.idDosesLabel.text = "Doses ${insulinDoses.size}"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDosesScreenBinding.inflate(inflater)
        Log.v("DEXCOM", "FragmentDosesScreen-onCreateView")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.idDoses.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterDoses
        }
        adapterDoses.setPenDoses(PenDataProvider.getInsulinPenDoseList())
        viewModel.insulinDosesLiveData.observe(viewLifecycleOwner, insulinDosesObserver)
    }


    override fun onDestroyView() {
        viewModel.insulinDosesLiveData.removeObserver(insulinDosesObserver)
        super.onDestroyView()
        Log.v("DEXCOM", "FragmentDosesScreen-onDestroyView")
    }
}