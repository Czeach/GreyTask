package com.czech.greytask.database

import com.czech.greytask.utils.testDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class RepositoriesDatabaseTest {

    @Mock
    private lateinit var queries: GreyTaskDatabaseQueries

    @Before
    fun before() {
        val database = testDatabase()
        queries = database.greyTaskDatabaseQueries
    }

    @Test
    fun insertAndRead() {
        queries.insertRepositories(
            id = 1,
            name = "test",
            fullName = "full test",
            description = "just for testing",
            stargazersCount = 4,
            language = "test language",
            topics = listOf("topic1", "topic2", "topic3")
        )
        queries.insertRepositories(
            id = 2,
            name = "test",
            fullName = "full test",
            description = "just for testing",
            stargazersCount = 4,
            language = "test language",
            topics = listOf("topic1", "topic2", "topic3")
        )

        Assert.assertEquals(2, queries.getRepositories().executeAsList().size)
    }
}