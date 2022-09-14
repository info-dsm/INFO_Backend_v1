package com.info.info_v1_backend.global.security.jwt

import com.info.info_v1_backend.global.security.jwt.auth.CustomAuthDetails
import com.info.info_v1_backend.global.security.jwt.auth.CustomAuthDetailsService
import com.info.info_v1_backend.global.security.jwt.exception.InvalidTokenException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtFilter(
    private val tokenProvider: TokenProvider,
    private val customAuthDetailsService: CustomAuthDetailsService
): OncePerRequestFilter() {

    companion object{
        const val AUTH = "Authorization"
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val subject: String? = getToken(request)?.let{return@let tokenProvider.getSubjectWithExpiredCheck(it)}
        subject?.let{
            val userDetails = customAuthDetailsService.loadUserByUsername(it)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails, it, userDetails.authorities)
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTH) ?: return null
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        throw InvalidTokenException(bearerToken)
    }
}