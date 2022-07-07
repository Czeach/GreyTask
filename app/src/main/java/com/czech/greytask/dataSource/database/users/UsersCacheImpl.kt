package com.czech.greytask.dataSource.database.users

import com.czech.greytask.database.GreyTaskDatabaseQueries
import com.czech.greytask.models.Users
import com.czech.greytask.utils.SQLDelightConverter.toUsersList
import javax.inject.Inject

class UsersCacheImpl @Inject constructor(
    private val queries: GreyTaskDatabaseQueries
): UsersCache {

    override fun insertUser(data: Users.Item) {
        queries.insertUser(
            id = data.id?.toLong(),
            login = data.login,
            avatarUrl = data.avatarUrl,
            type = data.type
        )
    }

    override fun insertUser(dataList: List<Users.Item>) {
        for (data in dataList) {
            insertUser(data)
        }
    }

    override fun getUsers(): List<Users.Item> {
        return queries.getUsers().executeAsList().toUsersList()
    }
}