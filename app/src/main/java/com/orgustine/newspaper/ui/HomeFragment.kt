package com.orgustine.newspaper.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.orgustine.newspaper.R
import com.orgustine.newspaper.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home), AdapterView.OnItemSelectedListener {
    private lateinit var binding : FragmentHomeBinding
    private var timePeriod = 1
    private val timeArray = arrayListOf(1, 7, 30) // corresponds to a day, a week, or a month of content.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.button.setOnClickListener {
            val text = binding.input.text.toString()
            val action = HomeFragmentDirections.actionHomeFragmentToNewsFragment(text, timePeriod)
            findNavController().navigate(action)
        }

        binding.streamAudio.setOnClickListener {
            findNavController().navigate(R.id.playerFragment)
        }

        binding.spinner.onItemSelectedListener = this
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeArray,
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