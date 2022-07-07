package com.czech.greytask.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    @SerialName("total_count")
    val totalCount: Int?,
    @SerialName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerialName("items")
    val items: List<Item>?,
    @SerialName("message")
    val message: String?
) {
    @Serializable
    data class Item(
        @SerialName("login")
        val login: String?,
        @SerialName("id")
        val id: Int?,
        @SerialName("avatar_url")
        val avatarUrl: String?,
        @SerialName("type")
        val type: String?
    )
}