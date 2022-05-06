package com.mal.cloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class CloudApplication

fun main(args: Array<String>) {
	runApplication<CloudApplication>(*args)
}
