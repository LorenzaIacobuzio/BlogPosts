package com.hatchways.blog.service

import com.hatchways.blog.model.Post
import com.hatchways.blog.model.User
import com.hatchways.blog.repository.PostRepository
import com.hatchways.blog.repository.UserRepository
import com.hatchways.blog.schema.PostRequest
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val modelMapper: ModelMapper
) : PostService {

    override fun createPost(postRequestBody: PostRequest, username: String): Post {
        val post: Post = modelMapper.map(postRequestBody, Post::class.java)
        val users: MutableSet<User> = mutableSetOf()
        val user: User = userRepository.findByUsername(username)
        users.add(user)
        post.users = users
        postRepository.save(post)
        postRepository.flush()
        return post
    }
}
