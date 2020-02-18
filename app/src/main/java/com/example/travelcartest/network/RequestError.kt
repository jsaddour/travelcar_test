package com.example.travelcartest.network


data class RequestError(
    var errorType: ErrorManager.ErrorType = ErrorManager.ErrorType.UNKNOWN_ERROR,
    var code: String = "",
    var message: String = "",
    var details: Map<String, String>? = null
)