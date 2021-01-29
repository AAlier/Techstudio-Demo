package com.techstudio.data.network.environment

class Environment(
    val baseAddress: String,
    val port: Int = -1,
    val isSslEnabled: Boolean,
    val apiVersion: Int
) {
    val restAddress: String =
        "${if (isSslEnabled) "https" else "http"}://$baseAddress${if (port != -1) ":$port" else ""}/svc/mostpopular/v$apiVersion"

    fun buildUrl(relativePath: String) =
        if (relativePath.isBlank()) {
            relativePath
        } else {
            "$restAddress/${relativePath.trimStart('/')}"
        }
}
