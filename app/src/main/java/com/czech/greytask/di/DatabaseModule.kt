package com.czech.greytask.di

import com.czech.greytask.Database
import com.czech.greytask.database.repositories.ReposDatabaseFactory
import com.czech.greytask.database.repositories.ReposDriverFactory
import com.czech.greytask.database.users.UsersDatabaseFactory
import com.czech.greytask.database.users.UsersDriverFactory
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
    fun provideRepositoriesDatabase(
        context: BaseApplication
    ): Database {
        return ReposDatabaseFactory(
            driverFactory = ReposDriverFactory(context = context)
        ).createDriver()
    }

    @Provides
    @Singleton
    fun provideUsersDatabase(
        context: BaseApplication
    ): Database {
        return UsersDatabaseFactory(
            driverFactory = UsersDriverFactory(context = context)
        ).createDriver()
    }
}