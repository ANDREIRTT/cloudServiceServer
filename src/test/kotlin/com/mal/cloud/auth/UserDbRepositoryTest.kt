package com.mal.cloud.auth

import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.data.table.Usr
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class UserDbRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserDbRepository

    @BeforeEach
    fun setupDb() {
        userRepository.saveAll(
            listOf(
                Usr(
                    username = "Bob",
                    password = "password user",
                    userRole = UserRole.ROLE_ADMIN
                ),
                Usr(
                    username = "Make",
                    password = "pass",
                    userRole = UserRole.ROLE_USER
                )
            )
        )
    }

    @Test
    fun queryUser() {
        val foundUser = userRepository.findUserByUsername("Bob")
        val notFoundUser = userRepository.findUserByUsername("Not found")

        Assertions.assertThat(foundUser).isNotNull
        Assertions.assertThat(notFoundUser).isNull()
    }
}
