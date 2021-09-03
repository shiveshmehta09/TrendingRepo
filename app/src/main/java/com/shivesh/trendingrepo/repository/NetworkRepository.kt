package com.shivesh.trendingrepo.repository

import com.shivesh.trendingrepo.BuildConfig
import com.shivesh.trendingrepo.data.TrendingRepositoryResponse
import com.shivesh.trendingrepo.network.TrendingRepositoryNetworkClient
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class NetworkRepository @Inject constructor() {
    /**
     * fun fetches trending repos from the API client
     * */
    fun getTrendingRepos(): Call<TrendingRepositoryResponse>? {
        return TrendingRepositoryNetworkClient.getInstance(BuildConfig.BASE_URL)
            .getTrendingRepos()
    }
}