package com.hatchways.blog.controller

import com.hatchways.blog.exception.BadRequestException
import com.hatchways.blog.exception.ExceptionController
import com.hatchways.blog.model.Post
import com.hatchways.blog.schema.GetResponseWrapper
import com.hatchways.blog.schema.PostResponse
import com.hatchways.blog.service.GetService
import org.modelmapper.ModelMapper
import org.modelmapper.TypeToken
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("/api")
class GetController(private val getService: GetService, private val modelMapper: ModelMapper) {

    /** Get the list of posts by author(s) in the database. */
    @GetMapping("/posts/{authorIds}")
    fun getPosts(
        @PathVariable authorIds: String,
        @RequestParam(name = "sortBy", required = false, defaultValue = "id") sortBy: String,
        @RequestParam(name = "direction", required = false, defaultValue = "asc") direction: String,
        authentication: Authentication
    ): ResponseEntity<GetResponseWrapper> {
        val exceptionController = ExceptionController()
        if (!validateRequestParameters(sortBy, direction)) {
            exceptionController.handleBadRequest(BadRequestException("Invalid query parameters"))
        }

        val posts: List<Post> = getService.getPosts(authorIds, sortBy, direction)
        val getResponse: List<PostResponse> = modelMapper.map(posts, object : TypeToken<List<PostResponse?>?>() {}.type)
        val response = GetResponseWrapper(getResponse)
        return ResponseEntity.ok(response)
    }

    private fun validateRequestParameters(sortBy: String, direction: String): Boolean {
        val allowedSortByValues = listOf("id", "reads", "likes", "popularity")
        val isSortByAllowed = allowedSortByValues.any{ v -> v.contains(sortBy) }
        val allowedDirectionValues = listOf("asc", "desc")
        val isDirectionAllowed = allowedDirectionValues.any{ v -> v.contains(direction) }

        return (isSortByAllowed && isDirectionAllowed)
    }
}
