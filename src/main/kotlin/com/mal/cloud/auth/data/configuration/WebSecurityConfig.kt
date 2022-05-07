package com.mal.cloud.auth.data.configuration

import com.mal.cloud.auth.data.security.JWTFilter
import com.mal.cloud.auth.data.security.UserDetailService
import com.mal.cloud.auth.data.table.UserRole
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableConfigurationProperties
class WebSecurityConfig(
    private val userDetailService: UserDetailService,
    private val jwtFilter: JWTFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .httpBasic().and()
            .authorizeRequests()
            .antMatchers("/account/login", "/account/create", "/account/list").permitAll()
            .antMatchers("/account/register", "/h2-console").hasRole(UserRole.ROLE_ADMIN.name)
            .anyRequest().authenticated()
            .and().sessionManagement().disable()
            .csrf().disable()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("")
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}