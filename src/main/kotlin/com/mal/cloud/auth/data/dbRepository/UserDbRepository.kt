package com.mal.cloud.auth.data.dbRepository

import com.mal.cloud.auth.data.table.Usr
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDbRepository : CrudRepository<Usr, Long> {
    fun findUserByUsername(username: String): Usr?
}