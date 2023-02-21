package com.sample.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.models.Businesses
import com.sample.pagination.R
import com.sample.utils.Constants


class PageAdapter :
    PagingDataAdapter<Businesses, PageAdapter.YelpViewHolder>(COMPARATOR) {

    class YelpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.findViewById<TextView>(R.id.restaurant_name)
        val addressTv = itemView.findViewById<TextView>(R.id.restaurant_address)
        val isOpenTv = itemView.findViewById<TextView>(R.id.restaurant_open)
        val logoIv = itemView.findViewById<ImageView>(R.id.restaurant_logo)
        val context = itemView.context
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: YelpViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.nameTv.text = item.name
            if (!item.location?.displayAddress.isNullOrEmpty()) {
                var address = ""
                for (item in item.location?.displayAddress!!) {
                    address += item
                }
                holder.addressTv.text = address
                holder.addressTv.visibility = View.VISIBLE
            } else {
                holder.addressTv.visibility = View.GONE
            }
            holder.isOpenTv.text = Constants.restaurantStatusMessage + if (item.isClosed != true) {
                Constants.closed
            } else {
                Constants.open
            }

            Glide.with(holder.context)
                .load(item.imageUrl) // image url
                .centerCrop()
                .into(holder.logoIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YelpViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.yelp_items_layout, parent, false)
        return YelpViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Businesses>() {
            override fun areItemsTheSame(oldItem: Businesses, newItem: Businesses): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Businesses, newItem: Businesses): Boolean {
                return oldItem == newItem
            }
        }
    }
}