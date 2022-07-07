package com.czech.greytask.utils.states

import com.czech.greytask.dataSource.models.Repositories

sealed class ReposState {
    data class Success(val data: List<Repositories.Item>?) : ReposState()
    data class Error(val message: String) : ReposState()
    object Loading : ReposState()
}