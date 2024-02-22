package com.hatchways.blog.controller

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class ControllerTestConfiguration {
    @Bean
    fun testUtil(): TestUtil {
        return TestUtil()
    }
}
