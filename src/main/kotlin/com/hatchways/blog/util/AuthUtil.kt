package com.hatchways.blog.util

import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant

class AuthUtil(
    private val tokenIssuer: String,
    private val tokenExpirationTime: Int,
    private val jwtEncoder: JwtEncoder
) {
    fun generateToken(username: String, expirationInSeconds: Int): String {
        val now = Instant.now()

        val claims = JwtClaimsSet.builder()
            .issuer(tokenIssuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expirationInSeconds.toLong()))
            .subject(username)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()
    }

    fun generateToken(username: String): String {
        return generateToken(username, tokenExpirationTime)
    }
}
