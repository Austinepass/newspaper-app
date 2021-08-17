package com.orgustine.newspaper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.orgustine.newspaper.databinding.ActivityMainBinding
import com.orgustine.newspaper.exoplayer.PlayerActivity

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var timePeriod = 1
    private val timeArray = arrayListOf(1, 7, 30) // corresponds to a day, a week, or a month of content.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val text = binding.input.text.toString()
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra("text_input", text)
            intent.putExtra("time_period", timePeriod)
            startActivity(intent)
        }

        binding.streamAudio.setOnClickListener {
            startActivity(Intent(this, PlayerActivity::class.java))
        }

        binding.spinner.onItemSelectedListener = this
        ArrayAdapter(this, android.R.layout.simple_spinner_item, timeArray,
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        timePeriod = timeArray[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}