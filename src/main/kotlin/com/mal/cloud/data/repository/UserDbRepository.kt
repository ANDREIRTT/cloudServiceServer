package com.mal.cloud.data.repository

import com.mal.cloud.data.table.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDbRepository : CrudRepository<User, Long> {
}