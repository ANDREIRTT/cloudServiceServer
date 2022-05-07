package com.mal.cloud.auth.data.table

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
data class User(
    private val username: String,
    private val password: String,
    val userRole: UserRole = UserRole.ROLE_USER,
    @Id
    @GeneratedValue
    val userId: Long? = null,
) : UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> {
        return listOf<GrantedAuthority>(SimpleGrantedAuthority(userRole.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}