package com.hatchways.blog.controller

import com.hatchways.blog.model.Post
import com.hatchways.blog.schema.PostRequest
import com.hatchways.blog.schema.PostResponse
import com.hatchways.blog.schema.PostResponseWrapper
import com.hatchways.blog.service.PostService
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PostController(private val postService: PostService, private val modelMapper: ModelMapper) {

    /** Create a new post in the database. */
    @PostMapping("/posts")
    fun createPost(
        @Valid @RequestBody postRequestBody: PostRequest,
        authentication: Authentication
    ): ResponseEntity<PostResponseWrapper> {
        val post: Post = postService.createPost(postRequestBody, authentication.name)
        val postResponse: PostResponse = modelMapper.map(post, PostResponse::class.java)
        val response = PostResponseWrapper(postResponse)
        return ResponseEntity.ok(response)
    }
}
