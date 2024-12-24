package com.example.ilmapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilmapp.R
import com.example.ilmapp.adapter.ButtonAdapter
import com.example.ilmapp.adapter.LessonAdapter
import com.example.ilmapp.adapter.SpaceItemDecoration
import com.example.ilmapp.databinding.FragmentHomeBinding
import com.example.ilmapp.viewmodel.ButtonViewModel
import com.example.ilmapp.viewmodel.LessonViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel: LessonViewModel by viewModels()
    private lateinit var adapter: LessonAdapter
    private val btnViewModel: ButtonViewModel by viewModels()
    private lateinit var btnAdapter: ButtonAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewLessons
        recyclerView.layoutManager =  LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.lessons.observe(viewLifecycleOwner) { lessonList ->
            adapter = LessonAdapter(lessonList)
            recyclerView.adapter = adapter
        }

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.size_xs)
        recyclerView.addItemDecoration(SpaceItemDecoration(spacingInPixels))

        val btnRecyclerView = binding.recyclerViewButtons
        btnRecyclerView.layoutManager =  LinearLayoutManager(this.context)
        btnViewModel.buttonGroups.observe(viewLifecycleOwner) { buttonGroups ->
            btnAdapter = ButtonAdapter(buttonGroups)
            btnRecyclerView.adapter = btnAdapter
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}