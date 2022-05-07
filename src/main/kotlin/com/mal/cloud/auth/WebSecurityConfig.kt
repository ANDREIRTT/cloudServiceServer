package com.mal.cloud.auth

import com.mal.cloud.auth.data.UserService
import com.mal.cloud.auth.data.table.UserRole
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableConfigurationProperties
class WebSecurityConfig(
    private val userService: UserService
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
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("")
    }
}