package com.dicoding.submission_githubuserapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.submission_githubuserapp.R
import com.dicoding.submission_githubuserapp.adapter.ListFollowPageAdapter
import com.dicoding.submission_githubuserapp.databinding.ActivityDetailUserBinding
import com.dicoding.submission_githubuserapp.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        showDetailUser(false)
        progessBar(true)

        val users = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, users)

        viewModel.setUserDetail(users.toString())
        viewModel.users.observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar)
                        .circleCrop()
                        .into(userAvatar)
                    username.text = it.username
                    name.text = it.name
                    tvTotalRepo.text = it.repository.toString()
                    tvTotalFollower.text = it.follower.toString()
                    tvTotalFollowing.text = it.following.toString()
                    company.text = it.company
                    tvLocation.text = it.location
                }
                showDetailUser(true)
                progessBar(false)
            }
        }

        val listFollowPageAdapter = ListFollowPageAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = listFollowPageAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
        supportActionBar?.elevation = 0f

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.userFavorited(id)
            withContext(Dispatchers.Main) {
                isFavorited(false)
                if (count != null) {
                    if (count > 0) {
                        isFavorited(true)
                        isChecked = true
                    }
                }
            }
        }

        binding.fabFavorited.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(users.toString(), id, avatar.toString())
                Toast.makeText(this, "Add To Favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteFavoriteUser(id)
                Toast.makeText(this, "Remove From Favorite", Toast.LENGTH_SHORT).show()
            }
            isFavorited(isChecked)
        }
    }

    private fun isFavorited(state: Boolean) {
        val fab = binding.fabFavorited
        if (state) {
            fab.setImageResource(R.drawable.ic_baseline_favorite_white_24)
        } else {
            fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun showDetailUser(state: Boolean) {
        binding.username.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.userAvatar.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.name.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvRepo.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvTotalRepo.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvFollower.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvTotalFollower.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvFollower.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvTotalFollower.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvFollowing.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvTotalFollowing.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.company.visibility = if (state) View.VISIBLE else View.INVISIBLE
        binding.tvLocation.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun progessBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_AVATAR = "EXTRA_AVATAR"
    }
}