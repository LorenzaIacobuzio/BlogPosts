package com.hatchways.blog.exception

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class CustomErrorAttributes : DefaultErrorAttributes() {

    /**
     * Customize the error attributes that will be present in error responses.
     *
     * Remove all unnecessary properties from the error response attributes.
     */
    override fun getErrorAttributes(webRequest: WebRequest, options: ErrorAttributeOptions): Map<String, Any> {
        val errorAttributes = super.getErrorAttributes(webRequest, options)

        errorAttributes.remove("path")
        errorAttributes.remove("status")
        errorAttributes.remove("timestamp")

        return errorAttributes
    }
}
