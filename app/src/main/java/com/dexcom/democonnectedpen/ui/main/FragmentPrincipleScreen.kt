package com.dexcom.democonnectedpen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dexcom.democonnectedpen.databinding.FragmentPrincipalScreenBinding
import com.dexcom.democonnectedpen.pendata.PenDataProvider
import com.dexcom.democonnectedpen.ui.pendata.PenAdapterPrincipalScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPrincipleScreen: Fragment() {

    private lateinit var binding: FragmentPrincipalScreenBinding
    private val viewmodel: FragmentPrincipleScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrincipalScreenBinding.inflate(inflater).apply {
            vm = viewmodel
            lifecycleOwner = viewLifecycleOwner
        }
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idDevicesConnected.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PenAdapterPrincipalScreen(PenDataProvider.PenDataList){
                viewmodel.toFragmentInformationPen(view,it, findNavController())
            }
        }
    }
}