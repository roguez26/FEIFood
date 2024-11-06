package com.feifood.feifood.model

import com.google.firebase.Timestamp
import java.util.Date

enum class PostStatus {
    ACTIVE,
    FINISHED
}

enum class PostType {
    SALE_POST,
    HOLD_POST
}

data class Post(
    val date: Date,
    val location: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val status: PostStatus,
    val type: PostType
)
