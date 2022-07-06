package com.dexcom.democonnectedpen

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.dexcom.democonnectedpen.ui.fragments.connections.FragmentConnectionsScreenViewModel
import com.dexcom.democonnectedpen.ui.fragments.peninformation.FragmentPenInformationViewModel
import com.dexcom.insulinpen.mediator.IInsulinPenMediator
import com.dexcom.insulinpen.mediator.InsulinPenMediator
import com.novonordisk.digitalhealth.dialoqsdk.manager.DialoqSdkApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ConnectedPenApplication : DialoqSdkApplication(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this@ConnectedPenApplication)
        setupDi()
    }

    private fun setupDi() {
        startKoin {
            androidContext(this@ConnectedPenApplication)
            modules(appModule, viewModelModule)
        }
    }

    private val appModule = module {
        single <IInsulinPenMediator> {
            InsulinPenMediator(androidContext(), ProcessLifecycleOwner.get())
        }
    }

    private val viewModelModule = module {
        single {
            ConnectedPenViewModel(insulinPenMediator = get())
        }
        viewModel {
            FragmentConnectionsScreenViewModel()
        }
        viewModel {
            FragmentPenInformationViewModel()
        }
    }
}