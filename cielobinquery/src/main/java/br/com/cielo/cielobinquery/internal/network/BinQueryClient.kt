package br.com.cielo.cielobinquery.internal.network

import br.com.cielo.cielobinquery.BuildConfig
import br.com.cielo.cielobinquery.Environment
import br.com.cielo.cielobinquery.internal.extensions.beared
import br.com.cielo.cielobinquery.internal.extensions.toStatusCode
import br.com.cielo.cielobinquery.CieloBinQueryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class BinQueryClient (private val environment: Environment, private val merchantId: String) {

    fun query(
        bin: String, token: String,
        onSuccessCallback: (model: CieloBinQueryResponse) -> Unit,
        onError: (error: String) -> Unit) {

        val webClient = WebClient(getEnvironmentUrl(environment))

        val call = webClient.createService(BinQueryApi::class.java).query(bin, token.beared(), merchantId)

        call.enqueue(object : Callback<CieloBinQueryResponse> {
            override fun onFailure(call: Call<CieloBinQueryResponse>, t: Throwable) {
                t.localizedMessage?.let { onError.invoke(it) }
            }

            override fun onResponse(
                call: Call<CieloBinQueryResponse>,
                responseCielo: Response<CieloBinQueryResponse>
            ) {
                if (responseCielo.isSuccessful) {
                    responseCielo.body()?.apply {
                        onSuccessCallback(responseCielo.body()!!)
                    }
                    if (responseCielo.body() == null)
                        onError.invoke("The response object is null.")
                }
                else {
                    onError.invoke("Error ${responseCielo.code()} - ${responseCielo.code().toStatusCode()}")
                }
            }
        })
    }

    private fun getEnvironmentUrl(environment: Environment): String {
        return if (environment == Environment.SANDBOX)
            BuildConfig.URL_QUERY_SANDBOX
        else
            BuildConfig.URL_QUERY_PRODUCTION
    }
}