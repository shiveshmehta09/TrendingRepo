package com.shivesh.trendingrepo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shivesh.trendingrepo.R
import com.shivesh.trendingrepo.data.TrendingRepoResponse
import com.shivesh.trendingrepo.utils.ImageUtils
import com.shivesh.trendingrepo.utils.NumberFormatter
import com.shivesh.trendingrepo.utils.ViewUtils
import com.shivesh.trendingrepo.vm.TrendingRepoViewModel
import kotlinx.android.synthetic.main.item_repository.view.*
import kotlin.properties.Delegates

/**
 * Created by Shivesh K Mehta on 01/09/21.
 * Version 2.0 KTX
 */
class TrendingRepoAdapter(var viewModel: TrendingRepoViewModel, var list: ArrayList<TrendingRepoResponse.ItemsObj>,
                          private val context: Context) : RecyclerView.Adapter<TrendingRepoAdapter.ReposViewHolder>() {
    /**
     * inflates the itemview
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(LayoutInflater.from(parent.context)
                                   .inflate(R.layout.item_repository, parent, false))
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

    inner class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            setSelectionPrev()

            itemView.layoutParent.setOnClickListener {
                selectedPosition = adapterPosition
                notifyItemChanged(adapterPosition)
                viewModel.setSelectedWithIndex(adapterPosition)
            }
        }

        fun bind(item: TrendingRepoResponse.ItemsObj, selected: Boolean) {
            itemView.apply { //using data binding to refer xml views directly
                txvTitle.text = item.name
                txvForks.text =
                    itemView.resources.getString(R.string.forks, NumberFormatter.formatDecimalNum(item.forksCount))
                txvDescription.text = item.description

                when {
                    item.forksCount > 0 -> imvFork.visibility = View.VISIBLE
                }
                val radius = 10
                ImageUtils.loadImage(context, item.owner.avatar, R.drawable.ic_launcher_foreground, imvAvatar, radius)
                layoutParent.isSelected = selected
            }
        }
    }

    /**
     * when the config changes & something was selected before config change
     * */
    fun setSelectionPrev() {
        if (selectedPosition == -1 && viewModel.getSelectedIndex().value != null) {
            selectedPosition = viewModel.getSelectedIndex().value!!
        }
    }

    override fun getItemCount(): Int {
        if (list.size < 1) {
            ViewUtils.showToast(context, context.resources.getString(R.string.msg_empty_string))
        }
        return list.size
    }

    /**
     * filtered list on user search
     * */
    fun updateList(repos: List<TrendingRepoResponse.ItemsObj>) {
        list = repos as ArrayList
        notifyDataSetChanged()
    }
}