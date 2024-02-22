package com.hatchways.blog.repository

import com.hatchways.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u from User as u where u._username = :username")
    fun findByUsername(username: String): User
}
