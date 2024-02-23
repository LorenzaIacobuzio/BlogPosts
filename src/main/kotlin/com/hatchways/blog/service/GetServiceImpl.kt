package com.hatchways.blog.service

import com.hatchways.blog.model.Post
import com.hatchways.blog.repository.PostRepository
import com.hatchways.blog.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : GetService {
    override fun getPosts(authorIds: String, sortBy: String, direction: String): List<Post>? {
        val userIds = authorIds.split(",").map { it.trim() }.map { it.toLong() }
        val isUserPresent = userRepository.findByUserId(userIds).isNotEmpty()

        if (!isUserPresent)
            return null

        val sortDirection = if (direction.contains("desc")) Sort.Direction.DESC else Sort.Direction.ASC
        val postsByAnyUserId: List<Post> = postRepository.findAllByUserId(userIds, Sort.by(sortDirection, sortBy))

        return postsByAnyUserId
    }
}
