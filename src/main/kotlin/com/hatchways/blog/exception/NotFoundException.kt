package com.hatchways.blog.exception

class NotFoundException(private val resource: String) : RuntimeException() {

    fun getResource(): String {
        return resource
    }
}
