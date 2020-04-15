package br.com.cielo.cielobinquery.internal.extensions

internal fun String.beared(): String {
    return "Bearer $this"
}