package br.com.cielo.cielobinquery.internal.network

import br.com.cielo.cielobinquery.CieloBinQueryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

internal interface BinQueryApi {
    @GET("1/cardBin/{bin}")
    fun query(
        @Path("bin") bin: String,
        @Header("Authorization") authorization: String,
        @Header("MerchantId") merchantId: String
    ) : Call<CieloBinQueryResponse>
}