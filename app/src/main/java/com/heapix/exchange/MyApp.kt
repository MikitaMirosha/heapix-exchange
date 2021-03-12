package com.heapix.exchange

import android.app.Application
import android.content.SharedPreferences
import com.heapix.exchange.net.repo.KeyboardRepo
import com.heapix.exchange.net.repo.PairExchangeRepo
import com.heapix.exchange.net.repo.StandardExchangeRepo
import com.heapix.exchange.net.services.ApiRest
import com.heapix.exchange.net.services.PairExchangeService
import com.heapix.exchange.net.services.StandardExchangeService
import com.heapix.exchange.utils.preferences.PreferencesUtils
import com.heapix.exchange.utils.rx.AppSchedulerProvider
import com.heapix.exchange.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.*
import retrofit2.Retrofit

private lateinit var kodeinStored: DI

class MyApp : Application() {

    private val settingModule = DI.Module("settings Module") {

        bind<Retrofit>() with singleton { ApiRest.getApi() }

        bind<CompositeDisposable>() with provider { CompositeDisposable() }

        bind<SchedulerProvider>() with singleton { AppSchedulerProvider() }

        bind<KeyboardRepo>() with singleton { KeyboardRepo() }

        bind<SharedPreferences>() with singleton {
            PreferencesUtils.getSharedPreferences(applicationContext)
        }

        bind<PairExchangeRepo>() with singleton {
            PairExchangeRepo(
                instance<Retrofit>().create(PairExchangeService::class.java),
                instance()
            )
        }

        bind<StandardExchangeRepo>() with singleton {
            StandardExchangeRepo(
                instance<Retrofit>().create(StandardExchangeService::class.java),
                instance()
            )
        }

    }

    companion object {
        var kodein: DI
            get() = kodeinStored
            set(_) {}

        fun isKodeinInitialized() = ::kodeinStored.isInitialized
    }

    override fun onCreate() {
        super.onCreate()

        if (::kodeinStored.isInitialized.not())
            kodeinStored = DI {
                import(settingModule)
            }
    }

}
