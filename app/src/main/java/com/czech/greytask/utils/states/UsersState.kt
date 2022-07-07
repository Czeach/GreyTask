package com.czech.greytask.utils.states

import com.czech.greytask.dataSource.models.Users

sealed class UsersState {
    data class Success(val data: List<Users.Item>?) : UsersState()
    data class Error(val message: String) : UsersState()
    object Loading : UsersState()
}