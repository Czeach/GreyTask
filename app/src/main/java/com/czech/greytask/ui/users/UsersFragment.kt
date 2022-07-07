package com.czech.greytask.ui.users

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
import com.czech.greytask.databinding.RepositoriesFragmentBinding
import com.czech.greytask.databinding.UsersFragmentBinding
import com.czech.greytask.ui.repos.RepositoriesViewModel
import com.czech.greytask.ui.repos.adapter.RepoDiffCallback
import com.czech.greytask.ui.repos.adapter.ReposAdapter
import com.czech.greytask.ui.users.adapter.UsersAdapter
import com.czech.greytask.ui.users.adapter.UsersDiffCallback
import com.czech.greytask.utils.states.ReposState
import com.czech.greytask.utils.states.UsersState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UsersFragment : Fragment() {

    private var _binding: UsersFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<UsersViewModel>()

    private val usersAdapter by lazy { UsersAdapter(UsersDiffCallback) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UsersFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFromDatabase()

        binding.repoRecycler.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        binding.searchButton.setOnClickListener {

            viewModel.usersState.value = null

            val query = binding.userSearchBarEditText.text.toString()

            if (query.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a repository name to search for", Toast.LENGTH_SHORT).show()
            }

            viewModel.getFromNetwork(query)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.usersState.collect {
                when (it) {
                    is UsersState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.stateLayout.visibility = View.GONE
                    }
                    is UsersState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is UsersState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.stateLayout.visibility = View.GONE
                        binding.repoRecycler.visibility = View.VISIBLE

                        if (it.data != null) {
                            usersAdapter.submitList(it.data)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

}