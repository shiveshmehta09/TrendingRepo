package com.shivesh.trendingrepo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shivesh.trendingrepo.R
import com.shivesh.trendingrepo.common.fetchImage
import com.shivesh.trendingrepo.common.formatDecimalNum
import com.shivesh.trendingrepo.common.showToast
import com.shivesh.trendingrepo.data.TrendingRepositoryResponse
import com.shivesh.trendingrepo.databinding.ItemRepositoryBinding
import com.shivesh.trendingrepo.vm.TrendingRepoViewModel
import kotlin.properties.Delegates

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class TrendingRepoAdapter(var viewModel: TrendingRepoViewModel,
                          var list: ArrayList<TrendingRepositoryResponse.Repositories>,
                          private val context: Context) : RecyclerView.Adapter<TrendingRepoAdapter.ReposViewHolder>() {
    /**
     * inflates the itemview
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReposViewHolder(binding)
    }

    /**
     * fun keeps track of the currently selected position
     */
    private var selectedPosition by Delegates.observable(-1) { _, oldPos, newPos ->
        if (oldPos != -1 && newPos in list.indices) {
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(list[position], (position == selectedPosition))
    }

    inner class ReposViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            setSelectionPrev()

            binding.layoutParent.setOnClickListener {
                selectedPosition = adapterPosition
                notifyItemChanged(adapterPosition)
                viewModel.setSelectedWithIndex(adapterPosition)
            }
        }

        fun bind(item: TrendingRepositoryResponse.Repositories, selected: Boolean) {
            binding.apply { //using data binding to refer xml views directly
                txvTitle.text = item.name
                txvForks.text = itemView.resources.getString(R.string.forks, formatDecimalNum(item.forksCount))
                txvDescription.text = item.description

                when {
                    item.forksCount > 0 -> binding.imvFork.visibility = View.VISIBLE
                }
                val radius = 10
                imvAvatar.fetchImage(context, item.owner.avatar, R.drawable.ic_launcher_foreground, radius)
                layoutParent.isSelected = selected
            }
        }
    }

    /**
     * when the configuration changed & something was selected before configuration changes
     * */
    fun setSelectionPrev() {
        if (selectedPosition == -1 && viewModel.getSelectedIndex().value != null) {
            selectedPosition = viewModel.getSelectedIndex().value!!
        }
    }

    override fun getItemCount(): Int {
        if (list.size < 1) {
            context.showToast(context.resources.getString(R.string.msg_empty_string))
        }
        return list.size
    }

    /**
     * filtered list on user search
     * */
    fun filteredList(repos: List<TrendingRepositoryResponse.Repositories>) {
        list = repos as ArrayList
        notifyDataSetChanged()
    }
}