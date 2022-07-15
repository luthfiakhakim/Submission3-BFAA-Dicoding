package com.dicoding.submission_githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission_githubuserapp.model.UserData
import com.dicoding.submission_githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listOfUserData: ArrayList<UserData>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setListUser(userData: ArrayList<UserData>) {
        listOfUserData.clear()
        listOfUserData.addAll(userData)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listOfUserData[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)
        holder.binding.tvUsername.text = user.login

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listOfUserData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listOfUserData.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }
}