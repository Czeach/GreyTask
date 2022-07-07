package com.czech.greytask.database.repositories

import com.czech.greytask.models.Repositories

interface ReposCache {

    fun insertRepository(data: Repositories.Item)

    fun insertRepository(dataList: List<Repositories.Item>)

    fun getRepository(): List<Repositories.Item>
}