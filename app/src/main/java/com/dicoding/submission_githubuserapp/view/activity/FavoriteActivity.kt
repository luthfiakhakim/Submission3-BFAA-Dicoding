package com.dicoding.submission_githubuserapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_githubuserapp.adapter.ListUserAdapter
import com.dicoding.submission_githubuserapp.database.ListFavoriteUser
import com.dicoding.submission_githubuserapp.databinding.ActivityFavoriteBinding
import com.dicoding.submission_githubuserapp.model.UserData
import com.dicoding.submission_githubuserapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private val listDetailUserData: ArrayList<UserData> = arrayListOf()
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Favorite User"

        adapter = ListUserAdapter(listDetailUserData)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = favoriteList(it)
                adapter.setListUser(list)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun favoriteList(users: List<ListFavoriteUser>): ArrayList<UserData> {
        val listUsers = ArrayList<UserData>()
        for (user in users){
            val userMapped = UserData (
                user.login,
                user.id,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}