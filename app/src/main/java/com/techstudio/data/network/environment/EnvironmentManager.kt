package com.techstudio.data.network.environment

import android.content.SharedPreferences
import androidx.core.content.edit

private const val ADDRESS_KEY = "ADDRESS_KEY"
private const val PORT_KEY = "PORT_KEY"
private const val API_VERSION = "API_VERSION"
private const val SSL_ENABLED_KEY = "SSL_ENABLED_KEY"

class EnvironmentManager(
    private val sharedPreferences: SharedPreferences
) {
    val environment = Environment(
        baseAddress = "api.nytimes.com",
        port = -1,
        apiVersion = 2,
        isSslEnabled = true
    )

    fun loadEnvironment() = with(sharedPreferences) {
        Environment(
            getString(ADDRESS_KEY, environment.baseAddress).orEmpty(),
            getInt(PORT_KEY, environment.port ?: -1),
            getBoolean(SSL_ENABLED_KEY, environment.isSslEnabled),
            getInt(API_VERSION, environment.apiVersion)
        )
    }

    fun saveEnvironment(
        sharedPreferences: SharedPreferences,
        environment: Environment
    ) {
        with(environment) {
            sharedPreferences.edit {
                putString(ADDRESS_KEY, baseAddress)
                putInt(PORT_KEY, port ?: -1)
                putInt(API_VERSION, apiVersion)
                putBoolean(SSL_ENABLED_KEY, isSslEnabled)
            }
        }
    }
}
