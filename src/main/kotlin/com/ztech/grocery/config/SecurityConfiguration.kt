package com.ztech.grocery.config

import com.ztech.grocery.auth.BearerAuthenticationFilter
import com.ztech.grocery.auth.BearerAuthenticationProvider
import com.ztech.grocery.auth.DaoAuthenticationService
import com.ztech.grocery.auth.JwtAuthenticationService
import com.ztech.grocery.repository.jpa.AccountRepository
import com.ztech.grocery.util.CryptoAESUtil
import com.ztech.grocery.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableMethodSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(
        httpSecurity: HttpSecurity,
        bearerAuthenticationFilter: BearerAuthenticationFilter
    ): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/accounts").permitAll()
                it.anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(bearerAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        bearerAuthenticationProvider: BearerAuthenticationProvider
    ): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.authenticationProvider(bearerAuthenticationProvider)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun daoAuthenticationProvider(
        passwordEncoder: BCryptPasswordEncoder,
        daoAuthenticationService: DaoAuthenticationService
    ) = DaoAuthenticationProvider().apply {
        setPasswordEncoder(passwordEncoder)
        setUserDetailsService(daoAuthenticationService)
    }

    @Bean
    fun jwtAuthenticationProvider(
        jwtAuthenticationService: JwtAuthenticationService
    ) = BearerAuthenticationProvider(jwtAuthenticationService)

    @Bean
    fun authenticationDetailsService(accountRepository: AccountRepository, cryptoAESUtil: CryptoAESUtil) =
        DaoAuthenticationService(accountRepository, cryptoAESUtil)

    @Bean
    fun jwtAuthenticationService(jwtUtil: JwtUtil) = JwtAuthenticationService(jwtUtil)
}