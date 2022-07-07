package com.czech.greytask.di

import com.czech.greytask.Database
import com.czech.greytask.dataSource.database.DriverFactory
import com.czech.greytask.dataSource.database.GreyTaskDatabaseFactory
import com.czech.greytask.dataSource.database.repositories.RepositoriesCache
import com.czech.greytask.dataSource.database.repositories.RepositoriesCacheImpl
import com.czech.greytask.dataSource.database.users.UsersCache
import com.czech.greytask.dataSource.database.users.UsersCacheImpl
import com.czech.greytask.database.GreyTaskDatabaseQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        context: BaseApplication
    ): Database {
        return GreyTaskDatabaseFactory(
            driverFactory = DriverFactory(context = context)
        ).createDriver()
    }

    @Provides
    @Singleton
    fun provideQueries(
        database: Database
    ): GreyTaskDatabaseQueries {
        return database.greyTaskDatabaseQueries
    }

    @Provides
    @Singleton
    fun provideRepositoriesCache(
        queries: GreyTaskDatabaseQueries
    ): RepositoriesCache {
        return RepositoriesCacheImpl(
            queries = queries
        )
    }

    @Provides
    @Singleton
    fun provideUsersCache(
        queries: GreyTaskDatabaseQueries
    ): UsersCache {
        return UsersCacheImpl(
            queries = queries
        )
    }

}