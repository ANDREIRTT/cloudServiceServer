package com.mal.cloud.auth.data

import org.springframework.beans.factory.BeanFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserBeanService(
    private val beanFactory: BeanFactory
) {
    fun getPasswordEncoder(): PasswordEncoder {
        return beanFactory.getBean(PasswordEncoder::class.java)
    }
}