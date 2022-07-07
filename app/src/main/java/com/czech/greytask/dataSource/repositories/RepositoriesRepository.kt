package com.czech.greytask.dataSource.repositories

import com.czech.greytask.dataSource.database.repositories.RepositoriesCache
import com.czech.greytask.dataSource.network.ApiService
import com.czech.greytask.models.Repositories
import com.czech.greytask.utils.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class RepositoriesRepository @Inject constructor(
    private val apiService: ApiService,
    private val repositoriesCache: RepositoriesCache
) {
    fun getReposFromNetwork(
        query: String
    ): Flow<DataState<List<Repositories.Item>>> {
        return flow {

            emit(DataState.loading())

            val networkResponse = apiService.getRepos(query)

            val reposList = networkResponse.body()?.items

            try {

                if (!networkResponse.isSuccessful) {
                    emit(DataState.error(message = "Error ${networkResponse.code()}: ${networkResponse.errorBody()}"))
                }

                if (networkResponse.isSuccessful && !reposList.isNullOrEmpty()) {
                    repositoriesCache.insertRepository(reposList)

                    val cacheResponse = repositoriesCache.getRepositories()
                    emit(DataState.data(data = cacheResponse))
                }

            }catch (e: Exception) {
                emit(DataState.error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getReposFromDatabase(): Flow<DataState<List<Repositories.Item>>> {
        return flow {

            emit(DataState.loading())

            val dataFromCache = repositoriesCache.getRepositories()

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