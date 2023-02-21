package com.sample.ui.main

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.adapter.PageAdapter
import com.sample.pagination.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var seekBar: SeekBar
    lateinit var radiusValue: TextView
    lateinit var adapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.restaurant_list)
        seekBar = findViewById(R.id.seekBar)
        radiusValue = findViewById(R.id.radius_value)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = PageAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        seekBar.max = 5000
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                radiusValue.text = if (progress < 1000) {
                    "${(progress)}m"
                } else {
                    val radius = mainViewModel.convertMeterToKilometer(progress.toFloat())
                    "${String.format("%.2f", radius)}Km"
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                updateRadius(seekBar.progress)
            }
        })
        updateRadius(seekBar.progress)
    }

    private fun updateRadius(progress: Int) {
        mainViewModel.getRestaurantsInProvidedRadius(progress).observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}