package com.shivesh.trendingrepo.network

import com.shivesh.trendingrepo.data.TrendingRepositoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
interface ApiService {
    @GET("search/repositories")
    fun getTrendingRepos(
        @Query("q") platform: String,
        @Query("per_page") itemsPerPage: Int,
        @Query("page") page: Int,
    ): Call<TrendingRepositoryResponse>
}