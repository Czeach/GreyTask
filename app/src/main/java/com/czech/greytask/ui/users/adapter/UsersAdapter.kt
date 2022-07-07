package com.czech.greytask.ui.users.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.greytask.R
import com.czech.greytask.dataSource.models.Users

class UsersAdapter(diffCallback: UsersDiffCallback) :
    ListAdapter<Users.Item, UsersAdapter.UsersViewHolder>(diffCallback) {

    class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = itemView.findViewById(R.id.fullName)
        private val type: TextView = itemView.findViewById(R.id.userName)
        private val avatar: ImageView = itemView.findViewById(R.id.userIcon)

        @SuppressLint("SetTextI18n")
        fun bind(data: Users.Item) {
            name.text = data.login
            type.text = data.type

            Glide.with(itemView)
                .load(data.avatarUrl)
                .into(avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)

        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}

object UsersDiffCallback : DiffUtil.ItemCallback<Users.Item>() {
    override fun areItemsTheSame(oldItem: Users.Item, newItem: Users.Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Users.Item, newItem: Users.Item): Boolean {
        return oldItem.id == newItem.id
    }
}