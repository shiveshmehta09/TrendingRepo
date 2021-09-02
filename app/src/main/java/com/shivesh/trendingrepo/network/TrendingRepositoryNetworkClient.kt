package com.shivesh.trendingrepo.network

import com.shivesh.trendingrepo.data.TrendingRepositoryResponse
import retrofit2.Call

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class TrendingRepositoryNetworkClient (baseUrl: String) : BaseNetworkClient<ApiService>(baseUrl) {
    private var page: Int = 1
    private var platform: String = "all"
    private var itemsCount: Int = 20

    companion object {
        private var INSTANCE: TrendingRepositoryNetworkClient? = null

        /**
         * although we could use object declaration instead of class to achieve singleton,
         * we do not do that here as objects cannot parse data.
         * Thus, we create a fun to get instance of the class
         * */
        fun getInstance(baseUrl: String): TrendingRepositoryNetworkClient {
            return when {
                INSTANCE != null -> INSTANCE!!
                else -> TrendingRepositoryNetworkClient(baseUrl)
            }
        }
    }

    /**
     * fun sets the service class for the retrofit builder in the base class
     * */
    override fun getApiServiceClass(): Class<out ApiService> {
        return ApiService::class.java
    }

    /**
     * service fun to fetch trending repos
     */
    fun getTrendingRepos(): Call<TrendingRepositoryResponse>? {
        return service?.getTrendingRepos(platform, itemsCount, page)
    }
}