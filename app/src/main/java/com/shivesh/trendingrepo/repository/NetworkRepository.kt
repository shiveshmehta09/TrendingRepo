package com.shivesh.trendingrepo.repository

import com.shivesh.trendingrepo.data.TrendingRepoResponse
import com.shivesh.trendingrepo.network.TrendingRepositoryNetworkClient
import retrofit2.Call

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class NetworkRepository {
    companion object {
        private var baseUrl: String = "https://api.github.com/"

        /**
         * fun fetches trending repos from the API client
         * */
        fun getTrendingRepos(): Call<TrendingRepoResponse>? {
            return TrendingRepositoryNetworkClient.getInstance(baseUrl).getTrendingRepos()
        }
    }
}