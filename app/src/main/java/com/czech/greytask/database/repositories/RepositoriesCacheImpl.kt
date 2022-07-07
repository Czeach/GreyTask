package com.czech.greytask.database.repositories

import com.czech.greytask.database.GreyTaskDatabaseQueries
import com.czech.greytask.models.Repositories
import javax.inject.Inject

class RepositoriesCacheImpl @Inject constructor(
    private val queries: GreyTaskDatabaseQueries
): RepositoriesCache {

    override fun insertRepository(data: Repositories.Item) {
        TODO("Not yet implemented")
    }

    override fun insertRepository(dataList: List<Repositories.Item>) {
        TODO("Not yet implemented")
    }

    override fun getRepositories(): List<Repositories.Item> {
        TODO("Not yet implemented")
    }
}