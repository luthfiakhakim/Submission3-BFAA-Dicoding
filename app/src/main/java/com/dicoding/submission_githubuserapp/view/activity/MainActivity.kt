package com.dicoding.submission_githubuserapp.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_githubuserapp.R
import com.dicoding.submission_githubuserapp.adapter.ListUserAdapter
import com.dicoding.submission_githubuserapp.databinding.ActivityMainBinding
import com.dicoding.submission_githubuserapp.model.UserData
import com.dicoding.submission_githubuserapp.utility.SettingPreferences
import com.dicoding.submission_githubuserapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val listUserData: ArrayList<UserData> = arrayListOf()
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: ListUserAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "\tGitHub User App"
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.logo_bar)
        }

        adapter = ListUserAdapter(listUserData)
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvListUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvListUser.setHasFixedSize(true)
            rvListUser.adapter = adapter
        }

        binding.searchBar.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    progessBar(true)
                    viewModel.setSearchUser(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        viewModel.getSearchUser().observe(this) {
            if (it != null && it.size != 0) {
                adapter.setListUser(it)
                searchUser(false)
            } else {
                adapter.setListUser(it)
                searchUser(true)
                showResult(true)
            }
        }

        viewModel.getLoading().observe(this) {
            if (it) {
                progessBar(true)
                binding.rvListUser.visibility = View.INVISIBLE
            } else {
                progessBar(false)
                binding.rvListUser.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                val favMenu = Intent(this, FavoriteActivity::class.java)
                startActivity(favMenu)
                return true
            }
            R.id.setting_menu -> {
                val settingMenu = Intent(this, SettingActivity::class.java)
                startActivity(settingMenu)
                return true
            }
            else -> return true
        }
    }

    private fun progessBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun searchUser(state: Boolean){
        binding.imgSearchUser.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showResult(state: Boolean) {
        val result = binding.imgSearchUser
        if (state) {
            result.setImageResource(R.drawable.nodata)
        }
    }
}