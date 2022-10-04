package com.info.info_v1_backend.global.security.jwt

import com.info.info_v1_backend.global.error.common.TokenCanNotBeNullException
import com.info.info_v1_backend.global.security.jwt.auth.CustomAuthDetailsService
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.security.jwt.env.JwtProperty
import com.info.info_v1_backend.global.security.jwt.exception.ExpiredTokenException
import com.info.info_v1_backend.global.security.jwt.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


@Component
class TokenProvider(
    private val jwtProperty: JwtProperty,
    private val customAuthDetailsService: CustomAuthDetailsService
){
    fun encode(subject: String): TokenResponse {
        return TokenResponse(
            Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
                .setSubject(subject)
                .claim("type", "access")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + (jwtProperty.accessExpiredAt * 1000)))
                .compact()
            ,
            Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
                .claim("type", "refresh")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + (jwtProperty.refreshExpiredAt * 1000)))
                .compact()
        )
    }

    fun decodeBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException(e.message.toString())
        }
    }

    fun getSubjectWithExpiredCheck(token: String): String {
        val body = decodeBody(token)
        val now = Date()
        if (now.after(Date(body.expiration.time))) throw ExpiredTokenException(token)
        return body.subject
            ?: throw TokenCanNotBeNullException("Subject is Null")
    }

    fun isExpired(token: String): Boolean {
        val body = Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJws(token).body
        val now = Date()
        return now.after(Date(now.time + body.expiration.time))
    }

    fun authentication(token: String): Authentication {
        val userDetails: UserDetails = customAuthDetailsService.loadUserByUsername(getSubjectWithExpiredCheck(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

}