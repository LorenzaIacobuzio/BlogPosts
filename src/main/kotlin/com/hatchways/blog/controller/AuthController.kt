package com.hatchways.blog.controller

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.hatchways.blog.model.User
import com.hatchways.blog.schema.AuthRequest
import com.hatchways.blog.schema.AuthResponse
import com.hatchways.blog.service.UserService
import com.hatchways.blog.util.AuthUtil

@RestController
@RequestMapping("/api")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val authUtil: AuthUtil,
    private val env: Environment,
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody authRequestBody: AuthRequest): ResponseEntity<AuthResponse> {
        val user = userService.createUser(authRequestBody.username, authRequestBody.password)
        val token = authUtil.generateToken(user.getUsername()!!)
        val response = AuthResponse(token!!, user.id!!, user.getUsername()!!)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody authRequestBody: AuthRequest): ResponseEntity<AuthResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequestBody.username, authRequestBody.password
            )
        )
        val user = authentication.principal as User
        val token = authUtil.generateToken(user.getUsername()!!)
        val response = AuthResponse(token!!, user.id!!, user.getUsername()!!)

        return ResponseEntity.ok(response)
    }
}
