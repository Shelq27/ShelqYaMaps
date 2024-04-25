package ru.netology.shelqyamaps.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import ru.netology.shelqyamaps.R
import ru.netology.shelqyamaps.adapter.MarkAdapter
import ru.netology.shelqyamaps.databinding.MarksFragmentBinding
import ru.netology.shelqyamaps.dto.Mark
import ru.netology.shelqyamaps.viewmodel.MapViewModel

class MarksFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MarksFragmentBinding.inflate(inflater, container, false)

        val viewModel by viewModels<MapViewModel>()

        val adapter = MarkAdapter(object : MarkAdapter.Listener {

            override fun onClick(mark: Mark) {
                findNavController().navigate(
                    R.id.action_marksFragment_to_mapFragment, bundleOf(
                        MapFragment.LAT_KEY to mark.latitude,
                        MapFragment.LONG_KEY to mark.longitude
                    )
                )
            }

            override fun onDelete(mark: Mark) {
                viewModel.deleteMarkById(mark.id)
            }

            override fun onEdit(mark: Mark) {
                AddMarkDialog.newInstance(lat = mark.latitude, long = mark.longitude, id = mark.id)
                    .show(childFragmentManager, null)
            }
        })

        binding.markList.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.marks.collectLatest { marks ->
                adapter.submitList(marks)
                binding.empty.isVisible = marks.isEmpty()
            }
        }

        return binding.root
    }
}