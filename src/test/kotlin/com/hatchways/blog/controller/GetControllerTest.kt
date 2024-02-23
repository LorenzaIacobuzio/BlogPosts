package com.hatchways.blog.controller

import com.hatchways.blog.BlogApplication
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
class GetControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var testUtil: TestUtil

    @Value("classpath:posts.json")
    private lateinit var postsJsonFile: Resource

    @Value("classpath:postsByLikesAsc.json")
    private lateinit var postsByLikesAscJsonFile: Resource

    @Value("classpath:postsByLikesDesc.json")
    private lateinit var postsByLikesDescJsonFile: Resource

    @Value("classpath:postsById.json")
    private lateinit var postsByIdJsonFile: Resource

    private val AUTHENTICATION_HEADER = "x-access-token"


    @Test
    fun `GET posts endpoint should return 401 when user is not logged in`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&sortBy=likes&direction=desc")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 401 when user token is invalid`() {
        val token = "invalid@Token"

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&sortBy=likes&direction=desc")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts by single author when author ID is valid`() {
        val postsData = postsJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=2")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts by authors when author ID list is valid`() {
        val postsData = postsJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts by authors when author ID list is valid - more authors`() {
        val postsData = postsByIdJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=3,2,1")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts by authors when at least one author ID exists`() {
        val postsData = postsJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=2,999")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 200 and empty list when no posts are associated to any author IDs`() {
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=6")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals("{\"posts\":[]}", content, true)
    }

    @Test
    fun `GET posts endpoint should return 404 when none of the author IDs exist`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=666,999")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"User not found\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 400 when author ID is negative number`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=-111")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 400 when author ID is invalid`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=invalid@Id")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 400 when at least one author ID is invalid`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2,invalid@Id")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 400 when author ID is empty`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 400 when author ID is whitespace`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds= ")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 400 when author ID query parameter is not present`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts sorted by id when sortBy is empty`() {
        val postsData = postsJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&sortBy=")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(postsData))
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 400 when sortBy is whitespace`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=2&sortBy= ")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts sorted by likes ascending when sortBy query parameter is likes`() {
        val postsData = postsByLikesAscJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&sortBy=likes")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(postsData))
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts sorted by ascending when direction is empty`() {
        val postsData = postsJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&direction=")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(postsData))
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 400 when direction is whitespace`() {
        val token = testUtil.getUserToken("santiago")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&direction= ")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Invalid query parameters\"}"))
            .andReturn()
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts sorted by likes ascending when sortBy query parameter is likes and direction is asc`() {
        val postsData = postsByLikesAscJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&sortBy=likes&direction=asc")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(postsData))
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }

    @Test
    fun `GET posts endpoint should return 200 and list of blog posts sorted by likes descending when sortBy query parameter is likes and direction is desc`() {
        val postsData = postsByLikesDescJsonFile.inputStream.readBytes().toString(Charsets.UTF_8)
        val token = testUtil.getUserToken("santiago")

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/posts?authorIds=1,2&sortBy=likes&direction=desc")
                .header(AUTHENTICATION_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(postsData))
            .andReturn()

        val content = result.response.contentAsString

        JSONAssert.assertEquals(postsData, content, true)
    }
}
