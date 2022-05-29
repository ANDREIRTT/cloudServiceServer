package com.mal.cloud.authFilter

import com.mal.cloud.auth.data.AuthFilterService
import com.mal.cloud.auth.data.AuthService
import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.data.table.Usr
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class AuthFilterServiceTest {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userDbRepository: UserDbRepository

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var authFilterService: AuthFilterService

    @BeforeEach
    fun initDb() {
        userDbRepository.save(
            Usr(
                username = "Bob",
                password = passwordEncoder.encode("bobPass"),
                userRole = UserRole.ROLE_ADMIN
            )
        )
    }

    @AfterEach
    fun clear() {
        userDbRepository.deleteAll()
    }

    @Test
    fun testValidFilter() {
        val userEntity = authService.login("Bob", "bobPass")
        val mockedRequest = MockHttpServletRequest()
        mockedRequest.addHeader("Authorization", "Bearer ${userEntity.token}")

        authFilterService.filter(mockedRequest)
    }

    @Test
    fun testBlankAuthBearer() {
        val mockedRequest = MockHttpServletRequest()
        mockedRequest.addHeader("Authorization", "Bearer   ")
        authFilterService.filter(mockedRequest)
    }

    @Test
    fun testInvalidAuthBearer() {
        val mockedRequest = MockHttpServletRequest()
        mockedRequest.addHeader("Authorization", "Bearer gyusfd")
        authFilterService.filter(mockedRequest)
    }

    @Test
    fun testNoAuthHeader() {
        authFilterService.filter(MockHttpServletRequest())
    }
}