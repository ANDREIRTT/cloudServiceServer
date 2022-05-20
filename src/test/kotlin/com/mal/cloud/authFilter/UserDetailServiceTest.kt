package com.mal.cloud.authFilter

import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.security.UserDetailService
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.data.table.Usr
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException

@SpringBootTest
class UserDetailServiceTest {
    @Autowired
    private lateinit var userRepository: UserDbRepository

    @Autowired
    private lateinit var userDetailService: UserDetailService

    @BeforeEach
    fun initDb() {
        userRepository.save(
            Usr(
                username = "Bob",
                password = "bobPass",
                userRole = UserRole.ROLE_ADMIN
            )
        )
    }

    @Test
    fun testService() {
        Assertions.assertThat(userDetailService.loadUserByUsername("Bob")).isNotNull

        Assertions.assertThatThrownBy {
            userDetailService.loadUserByUsername("not Bob")
        }.isInstanceOf(UsernameNotFoundException::class.java)
    }
}