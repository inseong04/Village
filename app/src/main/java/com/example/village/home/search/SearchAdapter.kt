package com.example.village.home.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.village.databinding.SearchwordItemBinding

class SearchAdapter (private val context: Context) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    var data = listOf<SearchWord>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.SearchViewHolder {
        val binding = SearchwordItemBinding.inflate(
                LayoutInflater.from(context), parent,false)

        return SearchViewHolder(binding)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.onBind(data[position])
    }

    class SearchViewHolder(val binding : SearchwordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data : SearchWord) {
            binding.wordDataClass = data
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

