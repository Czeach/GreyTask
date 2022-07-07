package com.czech.greytask.database.users

import com.czech.greytask.database.GreyTaskDatabaseQueries
import com.czech.greytask.models.Users
import javax.inject.Inject

class UsersCacheImpl @Inject constructor(
    private val queries: GreyTaskDatabaseQueries
): UsersCache {

    override fun insertUser(data: Users.Item) {
        TODO("Not yet implemented")
    }

    override fun insertUser(dataList: List<Users.Item>) {
        TODO("Not yet implemented")
    }

    override fun getUsers(): List<Users.Item> {
        TODO("Not yet implemented")
    }
}