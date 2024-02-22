package com.hatchways.blog.service

import com.hatchways.blog.model.Post
import com.hatchways.blog.schema.PostRequest

interface PostService {
    /** Create a new post in the database. */
    fun createPost(postRequestBody: PostRequest, username: String): Post
}
