package com.shivesh.trendingrepo.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivesh.trendingrepo.data.TrendingRepoResponse
import com.shivesh.trendingrepo.repository.NetworkRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class TrendingRepoViewModel(var stateHandle: SavedStateHandle) : ViewModel() {
    private var identifier: String = "SAVED_POS"

    fun getSelectedIndex(): LiveData<Int> {
        return stateHandle.getLiveData(identifier)
    }

    fun setSelectedWithIndex(position: Int) {
        stateHandle.set(identifier, position)
    }

    var reposList = MutableLiveData<TrendingRepoResponse>().apply {
        viewModelScope.launch { fetchRepos() }
    }

    /**
     * fun executes the fetch API call
     * */
    private fun fetchRepos() {
        val response =
            NetworkRepository.getTrendingRepos()
        response?.enqueue(object : Callback<TrendingRepoResponse> {
            override fun onResponse(
                call: Call<TrendingRepoResponse>,
                response: Response<TrendingRepoResponse>
            ) {
                reposList.postValue(response.body()) //updates the live data obj asynchronously
            }

            override fun onFailure(call: Call<TrendingRepoResponse>, t: Throwable) {
                t.message?.let { Log.e("error-----------> ", it) }
            }
        })
    }}