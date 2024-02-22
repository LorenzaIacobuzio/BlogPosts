package com.hatchways.blog.controller

import com.hatchways.blog.BlogApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.junit.jupiter.api.Test

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BlogApplication::class]
)
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:test.properties"])
@Import(ControllerTestConfiguration::class)
@Sql(
    scripts = ["/data.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = ["/cleanup.sql"],
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class PostControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var testUtil: TestUtil

    @Value("classpath:posts.json")
    private lateinit var postsJsonFile: Resource

    private val AUTHENTICATION_HEADER = "x-access-token";

    @Test
    fun testGetPostsForUserSuccess() {
        val postsData = postsJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")
        mockMvc.get("/api/posts?authorIds=2") {
            header(AUTHENTICATION_HEADER, token)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(postsData) }
        }
    }
}
