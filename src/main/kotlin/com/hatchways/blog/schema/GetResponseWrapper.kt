package com.hatchways.blog.schema

data class GetResponseWrapper(
    var posts: List<PostResponse>? = null
)
