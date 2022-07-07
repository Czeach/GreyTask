package com.czech.greytask.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.greytask.R
import com.czech.greytask.databinding.FragmentHomeBinding
import com.czech.greytask.databinding.RepositoriesFragmentBinding
import com.czech.greytask.ui.repos.adapter.RepoDiffCallback
import com.czech.greytask.ui.repos.adapter.ReposAdapter
import com.czech.greytask.utils.states.ReposState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RepositoriesFragment : Fragment() {

    private var _binding: RepositoriesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<RepositoriesViewModel>()

    private val repoAdapter by lazy { ReposAdapter(RepoDiffCallback) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RepositoriesFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFromDatabase()

        binding.repoRecycler.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = repoAdapter
        }

        binding.searchButton.setOnClickListener {

            viewModel.reposState.value = null

            val query = binding.repoSearchBarEditText.text.toString()

            if (query.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a repository name to search for", Toast.LENGTH_SHORT).show()
            }

            viewModel.getFromNetwork(query)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.reposState.collect {
                when (it) {
                    is ReposState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.stateLayout.visibility = View.GONE
                    }
                    is ReposState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ReposState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.stateLayout.visibility = View.GONE
                        binding.repoRecycler.visibility = View.VISIBLE

                        if (it.data != null) {
                            repoAdapter.submitList(it.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

}