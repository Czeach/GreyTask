package com.czech.greytask.di

import com.czech.greytask.dataSource.database.repositories.RepositoriesCache
import com.czech.greytask.dataSource.database.users.UsersCache
import com.czech.greytask.dataSource.network.ApiService
import com.czech.greytask.dataSource.repositories.RepositoriesRepository
import com.czech.greytask.dataSource.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoriesRepository(
        apiService: ApiService,
        repositoriesCache: RepositoriesCache
    ): RepositoriesRepository {
        return RepositoriesRepository(
            apiService = apiService,
            repositoriesCache = repositoriesCache
        )
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        apiService: ApiService,
        usersCache: UsersCache
    ): UsersRepository {
        return UsersRepository(
            apiService = apiService,
            usersCache = usersCache
        )
    }
}