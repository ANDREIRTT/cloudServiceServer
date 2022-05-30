package com.mal.cloud.uploadFiles.data.component

import com.mal.cloud.uploadFiles.data.database.table.FileType
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import javax.servlet.ServletContext

@Component
class DetermineFileType(private val servletContext: ServletContext) {

    fun getMediaType(fileName: String): MediaType {
        val mimeType = servletContext.getMimeType(fileName)
        return try {
            MediaType.parseMediaType(mimeType)
        } catch (e: Exception) {
            MediaType.APPLICATION_OCTET_STREAM
        }
    }

    fun getFileType(fileName: String): FileType {
        val mimeType = servletContext.getMimeType(fileName)
        return when {
            mimeType?.startsWith("image/") == true -> {
                FileType.IMAGE
            }
            mimeType?.startsWith("video/") == true -> {
                FileType.VIDEO
            }
            mimeType?.startsWith("text/") == true -> {
                FileType.TEXT
            }
            mimeType?.startsWith("audio/") == true -> {
                FileType.AUDIO
            }
            else -> {
                FileType.UNIDENTIFIED
            }
        }
    }
}