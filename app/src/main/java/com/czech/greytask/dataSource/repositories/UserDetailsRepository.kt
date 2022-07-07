package com.czech.greytask.dataSource.repositories

import com.czech.greytask.dataSource.network.ApiService
import com.czech.greytask.models.UserDetails
import com.czech.greytask.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class UserDetailsRepository(
    private val apiService: ApiService
) {
    fun getUserDetails(
        username: String
    ): Flow<DataState<UserDetails>> {
        return flow {

            emit(DataState.loading())

            val response = apiService.getUserDetails(username)

            try {
                when (response.isSuccessful) {
                    false -> {
                        emit(DataState.error(message = "Error ${response.code()}: ${response.errorBody()}"))
                    }
                    true -> {
                        emit(DataState.data(data = response.body()))
                    }
                }
            } catch (e: Exception) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}