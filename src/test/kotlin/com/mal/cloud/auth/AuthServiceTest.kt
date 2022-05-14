package com.mal.cloud.auth

import com.mal.cloud.auth.data.AuthService
import com.mal.cloud.auth.data.dbRepository.UserDbRepository
import com.mal.cloud.auth.data.exceptions.UserAlreadyExistException
import com.mal.cloud.auth.data.exceptions.UserInvalidValuesException
import com.mal.cloud.auth.data.table.UserRole
import com.mal.cloud.auth.data.table.Usr
import com.mal.cloud.auth.domain.entity.UserEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private lateinit var userDbRepository: UserDbRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var authService: AuthService

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
    fun clearDb() {
        userDbRepository.deleteAll()
    }

    @Test
    fun testRegisterUser() {
        val userEntity = authService.register(
            username = "user",
            password = "password",
            userRole = UserRole.ROLE_USER
        )

        assertUserEntity(userEntity)
    }

    @Test
    fun testRegisterUserExistException() {
        Assertions.assertThatThrownBy {
            authService.register("Bob", "dfsg", userRole = UserRole.ROLE_USER)
        }.isInstanceOf(UserAlreadyExistException::class.java)
    }

    @Test
    fun testUserLogin() {
        val userEntity = authService.login(
            username = "Bob",
            password = "bobPass"
        )
        assertUserEntity(userEntity)
    }

    @Test
    fun testUserLoginInvalidPassword() {
        Assertions.assertThatThrownBy {
            authService.login(
                username = "Bob",
                password = "bobPasss"
            )
        }.isInstanceOf(UserInvalidValuesException::class.java)
    }

    @Test
    fun testUserLoginNotFound() {
        Assertions.assertThatThrownBy {
            authService.login(
                username = "Bob2",
                password = "bobPasss"
            )
        }.isInstanceOf(UserInvalidValuesException::class.java)
    }

    fun assertUserEntity(userEntity: UserEntity) {
        Assertions.assertThat(userEntity.token).isNotNull
        Assertions.assertThat(userEntity.usr).hasNoNullFieldsOrProperties()
    }
}