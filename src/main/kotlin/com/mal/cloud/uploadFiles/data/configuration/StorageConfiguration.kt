package com.mal.cloud.uploadFiles.data.configuration

import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.unit.DataSize
import javax.servlet.MultipartConfigElement


@Configuration
class StorageConfiguration {

    @Bean
    fun multipartConfigElement(): MultipartConfigElement? {
        val factory = MultipartConfigFactory()
        factory.setMaxFileSize(DataSize.ofGigabytes(5))
        factory.setMaxRequestSize(DataSize.ofGigabytes(5))
        return factory.createMultipartConfig()
    }

    @Bean
    fun storageLocation(): LocationData {
        return LocationData("./build/storage")
    }
}