package com.bibbidi.habittracker.ui.addhabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentSelectHabitTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitTypeSelectionFragment : Fragment() {

    private var _binding: FragmentSelectHabitTypeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTheme(R.style.Theme_HabitTracker_Other)
        _binding = FragmentSelectHabitTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()
        setEventListener()
    }

    private fun setEventListener() {
        binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_selectionHabitTypeFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
