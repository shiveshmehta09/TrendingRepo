package com.shivesh.trendingrepo.vm

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivesh.trendingrepo.data.TrendingRepositoryResponse
import com.shivesh.trendingrepo.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
@HiltViewModel
class TrendingRepoViewModel @Inject constructor(var stateHandle: SavedStateHandle, val repository: NetworkRepository) : ViewModel() {
    private var identifier: String = "SAVED_POSITION"

    fun getSelectedIndex(): LiveData<Int> {
        return stateHandle.getLiveData(identifier)
    }

    fun setSelectedWithIndex(position: Int) {
        stateHandle.set(identifier, position)
    }

    var reposList = MutableLiveData<TrendingRepositoryResponse>().apply {
        viewModelScope.launch { fetchRepositories() }
    }

    /**
     * To fetch trending repos
     * */
    private fun fetchRepositories() {
        val response = repository.getTrendingRepos()
        response?.enqueue(object : Callback<TrendingRepositoryResponse> {
            override fun onResponse(call: Call<TrendingRepositoryResponse>,
                                    response: Response<TrendingRepositoryResponse>) {
                reposList.postValue(response.body()) //updates the live data obj asynchronously
            }

            override fun onFailure(call: Call<TrendingRepositoryResponse>, t: Throwable) {
                t.message?.let { Log.e("failed---> ", it) }
            }
        })
    }
}