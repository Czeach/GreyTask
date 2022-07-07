package com.czech.greytask.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.greytask.dataSource.repositories.UsersRepository
import com.czech.greytask.utils.states.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    val usersState = MutableStateFlow<UsersState?>(null)

    fun getFromNetwork(query: String) {
        viewModelScope.launch {
            usersRepository.getUsersFromNetwork(query).collect {
                when {
                    it.isLoading -> {
                        usersState.value = UsersState.Loading
                    }
                    it.data == null -> {
                        usersState.value = UsersState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { data ->
                            usersState.value = UsersState.Success(data = data)
                        }
                    }
                }
            }
        }
    }

    fun getFromDatabase() {
        viewModelScope.launch {
            usersRepository.getUsersFromDatabase().collect {
                when {
                    it.isLoading -> {
                        usersState.value = UsersState.Loading
                    }
                    it.data == null -> {
                        usersState.value = UsersState.Error(message = it.message.toString())
                    }
                    else -> {
                        it.data.let { data ->
                            usersState.value = UsersState.Success(data = data)
                        }
                    }
                }
            }
        }
    }
}