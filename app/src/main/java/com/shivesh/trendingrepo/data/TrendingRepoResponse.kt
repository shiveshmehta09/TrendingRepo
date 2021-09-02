package com.shivesh.trendingrepo.data
import com.google.gson.annotations.SerializedName

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class TrendingRepoResponse{lateinit var items: ArrayList<ItemsObj>
data class ItemsObj(
        var name: String?,
        var owner: Owner, var description: String?,
        @SerializedName("forks_count") var forksCount: Double
    )

    class Owner(@SerializedName("avatar_url") val avatar: String)
}