package com.hatchways.blog.service

import com.hatchways.blog.model.Post

interface GetService {
    /** Get a list of posts in the database. */
    fun getPosts(authorIds: String, sortBy: String, direction: String): List<Post>?
}
