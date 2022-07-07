package com.czech.greytask.database.repositories

import com.czech.greytask.models.Repositories

interface RepositoriesCache {

    fun insertRepository(data: Repositories.Item)

    fun insertRepository(dataList: List<Repositories.Item>)

    fun getRepositories(): List<Repositories.Item>
}