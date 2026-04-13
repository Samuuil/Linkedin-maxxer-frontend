package com.linkedinmaxxer.app

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.linkedinmaxxer.app.data.datasource.AuthDataSource
import com.linkedinmaxxer.app.data.datasource.AccountDataSource
import com.linkedinmaxxer.app.data.datasource.DashboardDataSource
import com.linkedinmaxxer.app.data.datasource.LMAccountDataSource
import com.linkedinmaxxer.app.data.datasource.LMAuthDataSource
import com.linkedinmaxxer.app.data.datasource.LMDashboardDataSource
import com.linkedinmaxxer.app.data.repository.LMAccountRepository
import com.linkedinmaxxer.app.data.repository.LMAuthRepository
import com.linkedinmaxxer.app.data.repository.LMDashboardRepository
import com.linkedinmaxxer.app.data.service.DashboardService
import com.linkedinmaxxer.app.data.session.AuthTokenInterceptor
import com.linkedinmaxxer.app.data.service.AuthService
import com.linkedinmaxxer.app.domain.repository.AccountRepository
import com.linkedinmaxxer.app.domain.repository.AuthRepository
import com.linkedinmaxxer.app.domain.repository.DashboardRepository
import com.linkedinmaxxer.app.domain.usecase.account.GetProfileUseCase
import com.linkedinmaxxer.app.domain.usecase.account.LogoutUseCase
import com.linkedinmaxxer.app.domain.usecase.auth.LoginUseCase
import com.linkedinmaxxer.app.domain.usecase.auth.RegisterUseCase
import com.linkedinmaxxer.app.ui.feature.auth.LoginViewModel
import com.linkedinmaxxer.app.ui.feature.auth.RegisterViewModel
import com.linkedinmaxxer.app.domain.usecase.dashboard.GetDashboardSummaryUseCase
import com.linkedinmaxxer.app.ui.feature.home.HomeViewModel
import com.linkedinmaxxer.app.ui.feature.settings.SettingsViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    factory { LoginUseCase(authRepository = lazy { get<AuthRepository>() }) }
    factory { RegisterUseCase(authRepository = lazy { get<AuthRepository>() }) }
    factory { GetDashboardSummaryUseCase(repository = lazy { get<DashboardRepository>() }) }
    factory { GetProfileUseCase(repository = lazy { get<AccountRepository>() }) }
    factory { LogoutUseCase(repository = lazy { get<AccountRepository>() }) }

    // Network - OkHttpClient
    single<OkHttpClient> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthTokenInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Network - Retrofit
    single {
        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        val okHttpClient = get<OkHttpClient>()
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single<AuthService> { get<Retrofit>().create(AuthService::class.java) }
    single<DashboardService> { get<Retrofit>().create(DashboardService::class.java) }
    factory<AuthDataSource> { LMAuthDataSource(get()) }
    factory<DashboardDataSource> { LMDashboardDataSource(get()) }
    factory<AccountDataSource> { LMAccountDataSource(get()) }
    factory<AuthRepository> { LMAuthRepository(get()) }
    factory<DashboardRepository> { LMDashboardRepository(get()) }
    factory<AccountRepository> { LMAccountRepository(get()) }

    viewModel { LoginViewModel(loginUseCase = get(), context = androidContext()) }
    viewModel { RegisterViewModel(registerUseCase = get(), context = androidContext()) }
    viewModel { HomeViewModel(getDashboardSummaryUseCase = get()) }
    viewModel { SettingsViewModel(getProfileUseCase = get(), logoutUseCase = get()) }
}
