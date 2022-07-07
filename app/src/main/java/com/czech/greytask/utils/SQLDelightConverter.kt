package com.czech.greytask.utils

import com.czech.greytask.database.Repositories_Entity
import com.czech.greytask.database.Users_Entity
import com.czech.greytask.models.Repositories
import com.czech.greytask.models.Users
import com.squareup.sqldelight.ColumnAdapter

object SQLDelightConverter {

    val listOfStringsAdapter = object : ColumnAdapter<List<String>, String> {
        override fun decode(databaseValue: String): List<String> {
            return if (databaseValue.isEmpty()) {
                listOf()
            } else {
                databaseValue.split(",")
            }
        }
        override fun encode(value: List<String>): String {
            return value.joinToString(separator = ",")
        }

    }

    private fun Repositories_Entity.toRepositories(): Repositories.Item {
        return Repositories.Item(
            id = id.toInt(),
            name = name,
            fullName = fullName,
            description = description,
            stargazersCount = stargazersCount?.toInt(),
            language = language,
            topics = topics
        )
    }

    fun List<Repositories_Entity>.toRepositoriesList(): List<Repositories.Item> {
        return map { it.toRepositories() }
    }

    private fun Users_Entity.toUsers(): Users.Item {
        return Users.Item(
            login = login,
            id = id.toInt(),
            avatarUrl = avatarUrl,
            type = type
        )
    }

    fun List<Users_Entity>.toUsersList(): List<Users.Item> {
        return map { it.toUsers() }
    }
}