package com.czech.greytask.ui.repos.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.czech.greytask.R
import com.czech.greytask.models.Repositories

class ReposAdapter(diffCallback: RepoDiffCallback):
    ListAdapter<Repositories.Item, ReposAdapter.RepoViewHolder>(diffCallback){

    class RepoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = itemView.findViewById(R.id.fullName)
        private val description: TextView = itemView.findViewById(R.id.repoDesc)
        private val stars: TextView = itemView.findViewById(R.id.starNoTextView)
        private val language: TextView = itemView.findViewById(R.id.languageTextView)

        @SuppressLint("SetTextI18n")
        fun bind(data: Repositories.Item) {
            name.text = data.fullName
            description.text = data.description

            if (data.stargazersCount == null) {
                stars.text = "0"
            } else {
                stars.text = data.stargazersCount.toString()
            }
            language.text = data.language
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)

        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}

object RepoDiffCallback : DiffUtil.ItemCallback<Repositories.Item>() {
    override fun areItemsTheSame(oldItem: Repositories.Item, newItem: Repositories.Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Repositories.Item, newItem: Repositories.Item): Boolean {
        return oldItem.id == newItem.id
    }
}