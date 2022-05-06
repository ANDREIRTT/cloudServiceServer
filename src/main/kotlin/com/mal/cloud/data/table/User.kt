package com.mal.cloud.data.table

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
data class User(
    val username: String,
    val password: String,
    @Id
    @GeneratedValue
    val userId: Long? = null,
)