package com.czech.greytask.dataSource.repositories

import com.czech.greytask.dataSource.network.ApiService
import com.czech.greytask.models.UserRepos
import com.czech.greytask.utils.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class UserReposRepository(
    private val apiService: ApiService
) {
    fun getUserRepos(
        username: String
    ): Flow<DataState<List<UserRepos>>> {
        return flow {

            emit(DataState.loading())

            val response = apiService.getUserRepos(username)

            val repos = response.body()

            try {
                if (!response.isSuccessful) {
                    emit(DataState.error(message = "Error ${response.code()}: ${response.errorBody()}"))
                }

                if (response.isSuccessful && !repos.isNullOrEmpty()) {
                    emit(DataState.data(data = repos))
                }
            } catch (e: Exception) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}