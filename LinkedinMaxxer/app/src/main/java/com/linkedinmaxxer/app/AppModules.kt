package com.linkedinmaxxer.app

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.linkedinmaxxer.app.data.datasource.AuthDataSource
import com.linkedinmaxxer.app.data.datasource.LMAuthDataSource
import com.linkedinmaxxer.app.data.repository.LMAuthRepository
import com.linkedinmaxxer.app.data.service.AuthService
import com.linkedinmaxxer.app.domain.repository.AuthRepository
import com.linkedinmaxxer.app.domain.usecase.auth.LoginUseCase
import com.linkedinmaxxer.app.domain.usecase.auth.RegisterUseCase
import com.linkedinmaxxer.app.ui.feature.auth.LoginViewModel
import com.linkedinmaxxer.app.ui.feature.auth.RegisterViewModel
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

    // Network - OkHttpClient
    single<OkHttpClient> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
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
    factory<AuthDataSource> { LMAuthDataSource(get()) }
    factory<AuthRepository> { LMAuthRepository(get()) }

    viewModel { LoginViewModel(loginUseCase = get(), context = androidContext()) }
    viewModel { RegisterViewModel(registerUseCase = get(), context = androidContext()) }
}
