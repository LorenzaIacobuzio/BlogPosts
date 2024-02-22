package com.hatchways.blog.service

import com.hatchways.blog.exception.UserExistsException
import com.hatchways.blog.model.User
import com.hatchways.blog.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService, UserDetailsService {

    override fun createUser(username: String, password: String): User {
        val existingUser: User? = userRepository.findByUsername(username)
        if (existingUser != null) {
            throw UserExistsException("User already exists")
        }

        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        val user = User(username, hashedPassword)
        userRepository.save(user)
        userRepository.flush()
        return user
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userRepository.findByUsername(username)
        if (user == null) {
            throw UsernameNotFoundException("User not found")
        }
        return user
    }
}
