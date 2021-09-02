package com.shivesh.trendingrepo.ui

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivesh.trendingrepo.R
import com.shivesh.trendingrepo.data.TrendingRepositoryResponse
import com.shivesh.trendingrepo.databinding.TrendingActivityBinding
import com.shivesh.trendingrepo.vm.TrendingRepoViewModel

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class TrendingActivity : AppCompatActivity() {
    private lateinit var viewModel: TrendingRepoViewModel
    private lateinit var listRepos: ArrayList<TrendingRepositoryResponse.Repositories>
    private lateinit var binding: TrendingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TrendingActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewModel = ViewModelProvider(this).get(TrendingRepoViewModel::class.java)
        binding.swipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
        binding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.blue))
        setTrendingRepoData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView
        searchView.apply {
            queryHint = resources.getString(R.string.txt_search)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        setSearchableQuery(searchView)
        searchView.setOnCloseListener {
            if (listRepos.size >= 0) updateList(listRepos)
            true
        }
        return true
    }

    fun updateList(list: List<TrendingRepositoryResponse.Repositories>) {
        (binding.rvTrending.adapter as TrendingRepoAdapter).filteredList(list)
    }

    override fun onBackPressed() {
        updateList(listRepos)
    }

    /**
     * adds search text listeners and updates the recycler view with filtered list
     * */
    private fun setSearchableQuery(sv: SearchView) {
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (listRepos.size > 0) {
                    updateFilteredList(query)
                }
                return true
            }

            override fun onQueryTextChange(entered: String): Boolean {
                if (entered.length >= 3) {
                    updateFilteredList(entered)
                }
                return true
            }

            private fun updateFilteredList(query: String) {
                val listUpdated = listRepos.filter {
                    (!it.description.isNullOrBlank() && it.description!!.contains(query,
                                                                                  true)) || (!it.name.isNullOrBlank() && it.name!!.contains(
                        query, true))
                }
                updateList(listUpdated)
            }
        })
    }

    private fun onRefresh() {
        getDataFromVM()
    }

    private fun setTrendingRepoData() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvTrending.apply {
            this.layoutManager = layoutManager
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }
        binding.pbLoader.visibility = View.VISIBLE
        getDataFromVM()
    }

    private fun getDataFromVM() {
        viewModel.reposList.observe(this, {
            if (it != null) {
                binding.rvTrending.adapter = TrendingRepoAdapter(viewModel, it.items, this)
                listRepos = it.items
            }
            binding.pbLoader.visibility = View.GONE
            binding.swipeRefreshLayout.apply {
                if (isRefreshing) isRefreshing = false
            }
        })
    }
}