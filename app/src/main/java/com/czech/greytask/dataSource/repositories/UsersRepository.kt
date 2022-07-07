package com.czech.greytask.dataSource.repositories

import com.czech.greytask.dataSource.database.users.UsersCache
import com.czech.greytask.dataSource.models.Users
import com.czech.greytask.dataSource.network.ApiService
import com.czech.greytask.utils.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val apiService: ApiService,
    private val usersCache: UsersCache
) {
    fun getUsersFromNetwork(
        query: String
    ): Flow<DataState<List<Users.Item>>> {
        return flow {

            emit(DataState.loading())

            val networkResponse = apiService.getUsers(query)

            val usersList = networkResponse.body()?.items

            try {

                if (!networkResponse.isSuccessful) {
                    emit(DataState.error(message = "Error ${networkResponse.code()}: ${networkResponse.errorBody()}"))
                }

                if (networkResponse.isSuccessful && !usersList.isNullOrEmpty()) {
                    usersCache.insertUser(usersList)

                    emit(DataState.data(data = usersList))
                }
            } catch (e: Exception) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUsersFromDatabase(): Flow<DataState<List<Users.Item>>> {
        return flow {
            emit(DataState.loading())

            val dataFromCache = usersCache.getUsers()

            try {
                emit(DataState.data(data = dataFromCache))
            } catch (e: Exception) {
                emit(
                    DataState.error(
                        message = e.message ?: "An error occurred"
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}