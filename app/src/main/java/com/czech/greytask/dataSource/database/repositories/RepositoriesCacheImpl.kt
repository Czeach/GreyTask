package com.czech.greytask.dataSource.database.repositories

import com.czech.greytask.database.GreyTaskDatabaseQueries
import com.czech.greytask.models.Repositories
import com.czech.greytask.utils.SQLDelightConverter.toRepositoriesList
import javax.inject.Inject

class RepositoriesCacheImpl @Inject constructor(
    private val queries: GreyTaskDatabaseQueries
): RepositoriesCache {

    override fun insertRepository(data: Repositories.Item) {
        queries.insertRepositories(
            id = data.id?.toLong(),
            name = data.name,
            fullName = data.fullName,
            description = data.description,
            stargazersCount = data.stargazersCount?.toLong(),
            language = data.language,
            topics = data.topics
        )
    }

    override fun insertRepository(dataList: List<Repositories.Item>) {
        for (data in dataList) {
            insertRepository(data)
        }
    }

    override fun getRepositories(): List<Repositories.Item> {
        return queries.getRepositories().executeAsList().toRepositoriesList()
    }
}