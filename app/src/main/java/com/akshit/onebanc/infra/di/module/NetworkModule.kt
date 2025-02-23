package com.akshit.onebanc.infra.di.module

import android.util.Log
import com.akshit.onebanc.BuildConfig
import com.akshit.onebanc.infra.network.WebApiInterface
import com.akshit.onebanc.infra.utils.ConnectivityManager
import com.akshit.onebanc.utilities.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        @Named("GSON") gsonConverter: Converter.Factory,
        @Named("Scalars") scalarsConverter: ScalarsConverterFactory,
        @Named("BaseURL") baseURL: String,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(scalarsConverter)
            .addConverterFactory(gsonConverter)
            .baseUrl(baseURL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("GSON")
    internal fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Named("Scalars")
    internal fun provideScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Named("BaseURL")
    fun providesBaseURL(): String {
        return WebApiInterface.BASE_URL
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(
        @Named("HttpLoggingInterceptor") interceptor: Interceptor,
        @Named("HeaderInterceptor") headerInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.addInterceptor(headerInterceptor)
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("HttpLoggingInterceptor")
    internal fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { this.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor
    }

    @Singleton
    @Provides
    @Named("HeaderInterceptor")
    fun getHeadersForApis(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val sb = StringBuilder()

            sb.append("****Headers Start****").append("\n")
            sb.append(CONTENT_TYPE_KEY).append(" : ").append(
                CONTENT_TYPE_VALUE_JSON
            ).append("\n")
            sb.append(X_PARTNER_API_KEY).append(" : ").append(
                BuildConfig.API_KEY
            ).append("\n")

            request = request.newBuilder()
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE_JSON)
                .addHeader(X_PARTNER_API_KEY, BuildConfig.API_KEY)
                .build()

            sb.append("****Headers Finish****")
            Log.e("web", sb.toString())
            Log.d("HeaderLog", sb.toString())
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager {
        return ConnectivityManager()
    }

    @Singleton
    @Provides
    internal fun provideWebApiInterface(retrofit: Retrofit): WebApiInterface {
        return retrofit.create(WebApiInterface::class.java)
    }
}