package com.czech.greytask.ui.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.greytask.dataSource.repositories.RepositoriesRepository
import com.czech.greytask.utils.states.ReposState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoriesViewModel @Inject constructor(
    private val repositoriesRepository: RepositoriesRepository
) : ViewModel() {

    val reposState = MutableStateFlow<ReposState?>(null)

    fun getFromNetwork(query: String) {
        viewModelScope.launch {
            repositoriesRepository.getReposFromNetwork(query).collect {
                when {
                    it.isLoading -> {
                        reposState.value = ReposState.Loading
                    }
                    it.data.isNullOrEmpty() -> {
                        reposState.value = ReposState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { data ->
                            reposState.value = ReposState.Success(data = data)
                        }
                    }
                }
            }
        }
    }

    fun getFromDatabase() {
        viewModelScope.launch {
            repositoriesRepository.getReposFromDatabase().collect {
                when {
                    it.isLoading -> {
                        reposState.value = ReposState.Loading
                    }
                    it.data.isNullOrEmpty() -> {
                        reposState.value = ReposState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { data ->
                            reposState.value = ReposState.Success(data = data)
                        }
                    }
                }
            }
        }
    }
}