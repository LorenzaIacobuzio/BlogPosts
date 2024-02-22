package com.hatchways.blog.service

import com.hatchways.blog.model.Post
import com.hatchways.blog.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class GetServiceImpl(
    private val postRepository: PostRepository
) : GetService {

    override fun getPosts(authorIds: String, sortBy: String, direction: String): List<Post> {
        val userIds = authorIds.split(",").map { it.trim() }.map { it.toLong() }
        val postsByAnyUserId: List<Post> = postRepository.findAllByAnyUserId(userIds, sortBy, direction)

        return postsByAnyUserId
    }
}
