package com.hatchways.blog.repository

import com.hatchways.blog.model.Post
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
interface PostRepository : JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT p FROM Post p JOIN p.users u WHERE u.id IN :userIds")
    fun findAllByUserId(userIds: List<Long>, sort: Sort): List<Post>
}
