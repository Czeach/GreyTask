package com.czech.greytask.dataSource.repositories

import com.czech.greytask.dataSource.database.users.UsersCache
import com.czech.greytask.dataSource.network.ApiService
import com.czech.greytask.models.Users
import com.czech.greytask.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
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

                    val cacheResponse = usersCache.getUsers()

                    emit(DataState.data(data = cacheResponse))
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