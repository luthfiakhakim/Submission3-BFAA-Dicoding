package com.dicoding.submission_githubuserapp.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_githubuserapp.adapter.ListUserAdapter
import com.dicoding.submission_githubuserapp.databinding.FragmentListFollowBinding
import com.dicoding.submission_githubuserapp.model.UserData
import com.dicoding.submission_githubuserapp.view.activity.DetailUserActivity
import com.dicoding.submission_githubuserapp.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private val listUserData: ArrayList<UserData> = arrayListOf()
    private var _binding: FragmentListFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowingViewModel>()
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentListFollowBinding.inflate(inflater, container, false)

        showRecycler()
        progessBar(true)

        viewModel.setListFollowingUser(username)
        viewModel.getListFollowingUser().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setListUser(it)
                progessBar(false)
            }
            if (it.count() != 0) {
                followingNotFound(false)
            } else {
                followingNotFound(true)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun showRecycler() {
        adapter = ListUserAdapter(listUserData)
        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(context)
            rvFollow.adapter = adapter
        }
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                Intent(activity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
    }

    private fun progessBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun followingNotFound(state: Boolean) {
        binding.notFound.visibility = if (state) View.VISIBLE else View.GONE
    }
}