/*
 * Copyright 2025 Zamulabs.
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
package com.zamulabs.dineeasepos.utils

import com.zamulabs.dineeasepos.data.ErrorResponse
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

suspend fun <T : Any> safeApiCall(
    cacheData: T? = null,
    apiCall: suspend () -> T,
): NetworkResult<T> {
    return try {
        val result = withContext(Dispatchers.IO) {
            apiCall()
        }
        NetworkResult.Success(data = result)
    } catch (e: RedirectResponseException) { // 3xx errors
        Napier.e("RedirectResponseException: ${e.response.status.value}")
        val networkError = parseNetworkError(
            errorResponse = e.response,
            exception = e,
        )
        NetworkResult.Error(
            data = cacheData,
            errorCode = e.response.status.value,
            errorMessage = networkError.errors.firstOrNull() ?: networkError.message ?: e.message,
        )
    } catch (e: ClientRequestException) { // 4xx errors
        Napier.e("ClientRequestException: ${e.response.status.value}")
        val networkError = parseNetworkError(
            errorResponse = e.response,
            exception = e,
        )

        NetworkResult.Error(
            data = cacheData,
            errorCode = e.response.status.value,
            errorMessage = networkError.errors.firstOrNull() ?: networkError.message ?: e.message,
        )
    } catch (e: ServerResponseException) { // 5xx errors
        Napier.e("ServerResponseException: ${e.response.status.value}")
        val networkError = parseNetworkError(
            errorResponse = e.response,
            exception = e,
        )
        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = networkError.errors.firstOrNull() ?: networkError.message ?: e.message,
            data = cacheData,
        )
    } catch (e: UnresolvedAddressException) {
        Napier.e("UnresolvedAddressException: ${e.message}")
        val networkError = parseNetworkError(
            exception = e,
        )
        NetworkResult.Error(
            data = cacheData,
            errorCode = 0,
            errorMessage = networkError.errors.firstOrNull() ?: networkError.message ?: e.message,
        )
    } catch (e: CancellationException) {
        Napier.e("CancellationException: ${e.message}")
        val networkError = parseNetworkError(
            exception = e,
        )
        NetworkResult.Error(
            data = cacheData,
            errorCode = 0,
            errorMessage = networkError.errors.firstOrNull() ?: networkError.message ?: e.message,
        )
    } catch (e: Exception) {
        Napier.e("Exception: ${e.message}")
        NetworkResult.Error(
            data = cacheData,
            errorCode = 0,
            errorMessage = "An unknown error occurred",
        )
    }
}

internal suspend fun parseNetworkError(
    errorResponse: HttpResponse? = null,
    exception: Exception? = null,
): ErrorResponse {
    return try {
        errorResponse?.bodyAsText()?.let {
            Napier.e("Error response: $it")
            Json.decodeFromString(ErrorResponse.serializer(), it)
        } ?: ErrorResponse(
            message = exception?.message ?: "An unknown error occurred",
        )
    } catch (e: Exception) {
        Napier.e("Error parsing error response: ${e.message}")
        ErrorResponse(
            message = exception?.message ?: "An unknown error occurred",
        )
    }
}
