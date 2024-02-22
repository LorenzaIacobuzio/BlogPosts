package com.hatchways.blog.controller

import com.hatchways.blog.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class TestUtil {

    @Autowired
    private lateinit var authUtil: AuthUtil

    fun getUserToken(userName: String): String {
        return authUtil.generateToken(userName)
    }
}
