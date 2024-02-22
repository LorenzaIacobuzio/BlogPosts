package com.hatchways.blog.controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Test

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:test.properties"])
@Sql(
    scripts = ["/cleanup.sql"],
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testLoginSuccess() {
        mockMvc.perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"thomas\",\"password\":\"123456\"}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.token").exists())
            .andExpect(jsonPath("\$.username").value("thomas"))
            .andExpect(jsonPath("\$.id").value(1))
            .andReturn()
    }
}
