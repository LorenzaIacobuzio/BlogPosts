package com.hatchways.blog.repository

import com.hatchways.blog.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT p FROM Post p JOIN p.users user WHERE user.id IN (:userIds) " +
            "ORDER BY " +
            "CASE WHEN :direction = 'asc' THEN " +
            "CASE " +
            "WHEN :sortBy = 'likes' THEN p.likes " +
            "WHEN :sortBy = 'id' THEN p.id " +
            "WHEN :sortBy = 'reads' THEN p.reads " +
            "WHEN :sortBy = 'popularity' THEN p.popularity " +
            "END " +
            "END ASC, " +
            "CASE WHEN :direction = 'desc' THEN " +
            "CASE " +
            "WHEN :sortBy = 'likes' THEN p.likes " +
            "WHEN :sortBy = 'id' THEN p.id " +
            "WHEN :sortBy = 'reads' THEN p.reads " +
            "WHEN :sortBy = 'popularity' THEN p.popularity " +
            "END " +
            "END DESC ")
    fun findAllByAnyUserId(userIds: List<Long>, sortBy: String, direction: String): List<Post>
}
