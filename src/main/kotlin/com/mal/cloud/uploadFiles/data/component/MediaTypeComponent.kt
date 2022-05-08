package com.mal.cloud.uploadFiles.data.component

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import javax.servlet.ServletContext

@Component
class MediaTypeComponent(private val servletContext: ServletContext) {

    fun getMediaTypeForFileName(fileName: String): MediaType {
        val mimeType = servletContext.getMimeType(fileName)
        return try {
            MediaType.parseMediaType(mimeType)
        } catch (e: Exception) {
            MediaType.APPLICATION_OCTET_STREAM
        }
    }
}