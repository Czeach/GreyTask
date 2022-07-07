package com.czech.greytask.database.users

import com.czech.greytask.models.Users

interface UsersCache {

    fun insertUser(data: Users.Item)

    fun insertUser(dataList: List<Users.Item>)

    fun getUsers(): List<Users.Item>
}