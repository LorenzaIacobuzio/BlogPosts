package com.hatchways.blog.schema

data class PostResponse(
    var id: Long? = null,
    var likes: Long? = null,
    var popularity: Float? = null,
    var reads: Long? = null,
    var tags: Array<String>? = null,
    var text: String? = null,
)