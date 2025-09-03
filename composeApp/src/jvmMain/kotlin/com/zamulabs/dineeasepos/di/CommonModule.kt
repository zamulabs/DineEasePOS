/*
 * Copyright 2024 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zamulabs.dineeasepos.di

import com.zamulabs.dineeasepos.data.ApiService.Companion.BASE_URL
import com.zamulabs.dineeasepos.data.PreferenceManager
import com.zamulabs.dineeasepos.data.SettingsRepository
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun commonModule(enableNetworkLogs: Boolean) = module {
    /**
     * Multiplatform-Settings
     */
    single<PreferenceManager> {
        PreferenceManager(settings = get())
    }
    /**
     * Creates a http client for Ktor that is provided to the
     * API client via constructor injection
     */
    single {
        HttpClient(CIO) {
            expectSuccess = true

            addDefaultResponseValidation()

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                }
            }

            if (enableNetworkLogs) {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            Napier.e(tag = "Http Client", message = message)
                        }
                    }
                }.also {
                    Napier.base(DebugAntilog())
                }
            }

            val resourcesResponse = Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
                encodeDefaults = true
                allowStructuredMapKeys = true
            }

            install(ContentNegotiation) {
                json(
                    json = resourcesResponse,
                )
            }

            install(ContentNegotiation) {
                json(
                    json = resourcesResponse,
                )
            }

            install(HttpRequestRetry) {
                retryIf { _, response -> response.status.value.let { it == 401 } }
                exponentialDelay()
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val settingsRepository: SettingsRepository by inject<SettingsRepository>()
                        val token = settingsRepository.getBearerToken().firstOrNull()
                        Napier.e {
                            "loadTokens: Access token: $token"
                        }
                        token?.let {
                            BearerTokens(it, "")
                        }
                    }
                }
            }
        }
    }

    /**
     * Dispatchers
     */
    single(named("IODispatcher")) { Dispatchers.Default }
}
