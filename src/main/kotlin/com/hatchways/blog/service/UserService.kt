package com.hatchways.blog.service

import com.hatchways.blog.model.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun createUser(username: String, password: String): User
}
