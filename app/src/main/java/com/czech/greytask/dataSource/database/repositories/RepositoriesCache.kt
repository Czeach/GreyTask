package com.czech.greytask.dataSource.database.repositories

import com.czech.greytask.dataSource.models.Repositories

interface RepositoriesCache {

    fun insertRepository(data: Repositories.Item)

    fun insertRepository(dataList: List<Repositories.Item>)

    fun getRepositories(): List<Repositories.Item>
}