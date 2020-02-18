package com.example.travelcartest.network

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.webkit.WebResourceError
import android.webkit.WebResourceResponse
import android.widget.Toast
import com.example.travelcartest.R
import com.example.travelcartest.TravelCarTestApp
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Successful requests
 * successful request: HTTP 200 Success, HTTP 204 No Content or HTTP 201 Created
 * =========================================================
 * Errors
 * Errors responses are formatted using this pattern
 * - not allowed request: HTTP 401 Unauthorized
 * - bad request: HTTP 400 Bad Request
 * - internal server error: HTTP 500 Internal Server Error
 * - not supported function: HTTP 405 Method Not Allowed
 * - too many requests: HTTP 429 Too Many Requests
 * If the request fails, the response contains a field “error” that contains,
 * an “error code” such as:
 * “BadRequest” Malformed Request
 * “Error” Generic Error
 * “Unauthorized” Unauthorized Request
 * “Unsupported”: Function not available or no longer available
 * “Throttled” Too many requests
 * “NotFound” Object not found
 * An optional message, translated into the language of the request, detailing the error
 * An optional error id, allowing a posteriori diagnosis (corresponding to our bug tracking system)
 * An optional dictionary “details”, indicating for each field of the request for an error message explaining that the error
 * Warning: some responses generated upstream of our API are likely not meet this format (404, 502, 503, …)
 */
object ErrorManager {

    enum class ErrorType {
        UNKNOWN_ERROR,
        CAPTIVE_PORTAL_ERROR, // Captive error
        TIMEOUT_ERROR, // Timeout
        NO_CONNECTIVITY_ERROR, // No connectivity
        OAUTH_ERROR,
        API_ERROR,
        OAUTH_TOKEN_DEPRECATED, // 403
        API_METHOD_DEPRECATED // 410
    }

    fun getRequestError(message: String): RequestError {
        return RequestError().apply {
            this.errorType = ErrorType.UNKNOWN_ERROR
            this.message = message
        }
    }

    fun getRequestError(errorCode: Int, description: String): RequestError {
        return RequestError().apply {
            this.errorType = ErrorType.UNKNOWN_ERROR
            this.code = errorCode.toString()
            this.message = description
            if (description.contains("ERR_INTERNET_DISCONNECTED")) {
                this.errorType = ErrorType.NO_CONNECTIVITY_ERROR
                this.message = TravelCarTestApp.instance.getString(R.string.error_connection)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun getRequestError(error: WebResourceError): RequestError {
        return RequestError().apply {
            this.errorType = ErrorType.UNKNOWN_ERROR
            this.code = error.errorCode.toString()
            this.message = error.description.toString()
            if (this.message.contains("ERR_INTERNET_DISCONNECTED")) {
                this.errorType = ErrorType.NO_CONNECTIVITY_ERROR
                this.message = TravelCarTestApp.instance.getString(R.string.error_connection)
            }
        }
    }

    fun getRequestError(errorResponse: WebResourceResponse): RequestError {
        return RequestError().apply {
            this.code = errorResponse.statusCode.toString()
            this.message = errorResponse.reasonPhrase
            this.details = errorResponse.responseHeaders
        }
    }

    fun getRequestError(throwable: Throwable): RequestError {
        val requestError = RequestError().apply {
            when (throwable) {
                // java.net.UnknownHostException: Unable to resolve host [...]: No address associated with hostname
                is NoConnectivityException -> {
                    this.errorType = ErrorType.NO_CONNECTIVITY_ERROR
                    this.message = TravelCarTestApp.instance.getString(R.string.error_connection)
                }

                // Timeout
                is TimeoutException -> {
                    this.errorType = ErrorType.TIMEOUT_ERROR
                    this.message = TravelCarTestApp.instance.getString(R.string.error_timeout)
                }

                // Captive Portal
                is CaptivePortalException -> {
                    this.errorType = ErrorType.CAPTIVE_PORTAL_ERROR
                    this.message = TravelCarTestApp.instance.getString(R.string.error_captive_portal)
                }



                else -> {
                    this.errorType = ErrorType.UNKNOWN_ERROR
                    this.message = TravelCarTestApp.instance.getString(R.string.error_occurred)
                }
            }
        }

        // {"status": "404", "message": "Page not found"}
        if (requestError.message.isEmpty()) {
            requestError.message = TravelCarTestApp.instance.getString(R.string.error_occurred)
        }

        return requestError
    }

    fun displayError(context: Context?, requestError: RequestError) {
        if (context != null) {
            displayError(context, requestError.message)
        }
    }


    fun displayError(context: Context?, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

// https://medium.com/@janczar/http-errors-with-kotlin-rx-and-retrofit-34e905aa91dd

open class NetworkException(error: Throwable) : RuntimeException(error)

class NoConnectivityException(error: Throwable) : NetworkException(error)

class TimeoutException(error: Throwable) : NetworkException(error)

class CaptivePortalException(error: Throwable) : NetworkException(error)

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            // No connectivity
            is UnknownHostException -> Single.error(NoConnectivityException(error))

            // Timeout
            is SocketTimeoutException -> Single.error(TimeoutException(error))

            // TODO: Captive Portal
            // Captive Portal
//            is CaptivePortal -> Single.error(CaptivePortalException(error))

            // Http error
            else -> Single.error(error)
        }
    }