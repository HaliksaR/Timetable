package ru.fevgenson.timetable.features.dictionary.presentation.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class CategoryItemAdapter : ListAdapter<String, CategoryItemViewHolder>(CategoryItemDiffUtils()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryItemViewHolder = CategoryItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

private class CategoryItemDiffUtils : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}