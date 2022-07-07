package com.czech.greytask.database.users

import com.czech.greytask.models.Users

interface UsersCache {

    fun insertRepository(data: Users.Item)

    fun insertRepository(dataList: List<Users.Item>)

    fun getRepository(): List<Users.Item>
}