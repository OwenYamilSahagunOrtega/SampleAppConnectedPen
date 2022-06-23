package com.dexcom.democonnectedpen

import android.app.Application
import com.dexcom.democonnectedpen.ui.lilly.LillyViewModel
import com.dexcom.insulinpen.mediator.InsulinPenMediator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PenApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDi()
    }

    private fun setupDi() {
        startKoin {
            androidContext(this@PenApplication)
            modules(appModule, viewModelModule)
        }
    }

    private val appModule = module {
        single { InsulinPenMediator(androidContext()) }
    }

    private val viewModelModule = module {
        viewModel {
            LillyViewModel(insulinPenMediator = get())
        }
        viewModel {
            FragmentPrincipleScreenViewModel()
        }
        viewModel {
            FragmentPenInformationViewModel()
        }
        viewModel {
            DialogFragmentDevicesAvailableViewModel()
        }
    }
}