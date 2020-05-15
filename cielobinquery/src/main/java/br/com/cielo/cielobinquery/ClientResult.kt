package br.com.cielo.cielobinquery

data class ClientResult <T>(
    var result: T?,
    var statusCode: HttpStatusCode,
    var errors: List<ErrorResponse?> = listOf()
)