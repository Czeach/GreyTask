package com.czech.greytask.dataSource.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repositories(
    @SerialName("total_count")
    val totalCount: Int?,
    @SerialName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerialName(" items")
    val items: List<Item>?,
    @SerialName("message")
    val message: String?
) {
    @Serializable
    data class Item(
        @SerialName("id")
        val id: Int?,
        @SerialName("name")
        val name: String?,
        @SerialName("full_name")
        val fullName: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("stargazers_count")
        val stargazersCount: Int?,
        @SerialName("language")
        val language: String?,
        @SerialName("topics")
        val topics: List<String>?,
    )
}