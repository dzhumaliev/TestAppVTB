package com.io.testappvtb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.io.testappvtb.R
import com.io.testappvtb.data.models.VideoResultModel
import com.io.testappvtb.databinding.ActivityMainBinding
import com.io.testappvtb.ui.fragments.FirstViewModel
import com.io.testappvtb.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var collectionAdapter: CollectionAdapter

    private val viewModel: FirstViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getVideo()

        viewModel.state.observe(this@MainActivity, { response ->
            response.results?.let { initAdapter(it) }

        })

        viewModel.stateError.observe(this, {
            binding.tvError.visible()
            binding.tvError.text = it
        })
    }

    private fun initAdapter(response: VideoResultModel) {
        collectionAdapter = CollectionAdapter(this@MainActivity, response)

        binding.pager.offscreenPageLimit = 4

        binding.pager.adapter = collectionAdapter


        TabLayoutMediator(binding.tab, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.one)
                1 -> tab.text = getString(R.string.two)
                2 -> tab.text = getString(R.string.three)
                3 -> tab.text = getString(R.string.four)
            }
        }.attach()

    }

}