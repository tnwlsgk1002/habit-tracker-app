package com.bibbidi.habittracker.ui.addhabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentSetHabitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetHabitFragment : Fragment() {

    private lateinit var viewModel: SetHabitViewModel

    private var _binding: FragmentSetHabitBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTheme(R.style.Theme_HabitTracker_Other)

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = ""
        }?.show()

        _binding = FragmentSetHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
