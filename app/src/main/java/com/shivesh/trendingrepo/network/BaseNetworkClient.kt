package com.shivesh.trendingrepo.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
abstract class BaseNetworkClient<S>(baseURL: String) {
    private var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit
    var service: S? = null

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(getGsonConverter()))
            .client(httpClient.build())
            .build()
        createAPIService()
    }

    abstract fun getApiServiceClass(): Class<out S>

    /**
     * fun creates retrofit with the provided service class
     * */
    private fun createAPIService() {
        service = retrofit.create(getApiServiceClass())
    }

    /**
     * fun to get GSON
     */
    private fun getGsonConverter(): Gson {
        return GsonBuilder().create()
    }
}