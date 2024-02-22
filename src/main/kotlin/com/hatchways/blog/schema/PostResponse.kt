package com.hatchways.blog.schema

data class PostResponse(
    var text: String? = null,
    var tags: Array<String>? = null,
    var popularity: Float? = null,
    var reads: Long? = null,
    var likes: Long? = null,
    var id: Long? = null
)
