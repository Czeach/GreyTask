package com.czech.greytask.database

import com.czech.greytask.utils.testDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class UsersDatabaseTest {

    @Mock
    private lateinit var queries: GreyTaskDatabaseQueries

    @Before
    fun before() {
        val database = testDatabase()
        queries = database.greyTaskDatabaseQueries
    }

    @Test
    fun insertAndRead() {
        queries.insertUser(
            id = 1,
            login = "test user",
            avatarUrl = "long url thingy",
            type = "this is the type"
        )
        queries.insertUser(
            id = 2,
            login = "test user",
            avatarUrl = "long url thingy",
            type = "this is the type"
        )

        Assert.assertEquals(2, queries.getUsers().executeAsList().size)
    }
}